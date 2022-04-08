package com.imaec.triplan.ui.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imaec.domain.model.CityDto
import com.imaec.domain.model.PlanDto
import com.imaec.domain.usecase.plan.AddPlanUseCase
import com.imaec.triplan.ext.addSourceList
import com.imaec.triplan.ui.calendar.CalendarActivity.Companion.CITY
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val addPlanUseCase: AddPlanUseCase
) : ViewModel() {

    private val _state = MutableLiveData<CalendarState>()
    val state: LiveData<CalendarState> get() = _state

    private val _startDate = MutableLiveData<LocalDate?>(null)
    val startDate: LiveData<LocalDate?> get() = _startDate

    private val _endDate = MutableLiveData<LocalDate?>(null)
    val endDate: LiveData<LocalDate?> get() = _endDate

    private val city = savedStateHandle.get<CityDto>(CITY)

    private val _buttonText = MediatorLiveData<String>().apply {
        addSourceList(startDate, endDate) {
            selectedDate()
        }
    }
    val buttonText: LiveData<String> get() = _buttonText

    private fun selectedDate(): String {
        return when {
            startDate.value != null && endDate.value == null -> {
                "${dateToString(startDate.value)} 일정 추가"
            }
            startDate.value != null && endDate.value != null -> {
                "${dateToString(startDate.value)} ~ ${dateToString(endDate.value)} 일정 추가"
            }
            else -> ""
        }
    }

    private fun dateToString(date: LocalDate?): String =
        DateTimeFormatter.ofPattern("yyyy.MM.dd(E)").format(date)

    fun clearPlan() {
        _startDate.value = null
        _endDate.value = null
    }

    fun onClickMonth(month: CalendarMonth) {
        _state.value = CalendarState.OnClickMonth(month)
    }

    fun onClickDay(day: CalendarDay) {
        if (day.owner == DayOwner.THIS_MONTH) {
            val date = day.date
            if (startDate.value != null) {
                if (date < startDate.value || endDate.value != null) {
                    _startDate.value = date
                    _endDate.value = null
                } else if (date != startDate.value) {
                    _endDate.value = date
                }
            } else {
                _startDate.value = date
            }

            _state.value = CalendarState.OnClickDay
        }
    }

    fun onClickAdd() {
        buttonText.value ?: return
        city ?: return

        val planName =
            "${DateTimeFormatter.ofPattern("yy년 MM월").format(startDate.value)} ${city.city} 여행"
        viewModelScope.launch {
            addPlanUseCase(
                PlanDto(
                    planName = planName,
                    planItemList = listOf(),
                    city = city.city,
                    startDate = startDate.value!!,
                    endDate = endDate.value!!,
                )
            )
            _state.value = CalendarState.OnClickAdd
        }
    }
}

package com.imaec.triplan.ui.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.imaec.triplan.ext.addSourceList
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableLiveData<CalendarState>()
    val state: LiveData<CalendarState> get() = _state

    private val _startDate = MutableLiveData<LocalDate?>(null)
    val startDate: LiveData<LocalDate?> get() = _startDate

    private val _endDate = MutableLiveData<LocalDate?>(null)
    val endDate: LiveData<LocalDate?> get() = _endDate

    private val _buttonText = MediatorLiveData<String>().apply {
        addSourceList(startDate, endDate) {
            selectedDate()
        }
    }
    val buttonText: LiveData<String> get() = _buttonText

    private fun selectedDate(): String {
        val df = DateTimeFormatter.ofPattern("yyyy.MM.dd(E)")
        return when {
            startDate.value != null && endDate.value == null -> {
                "${df.format(startDate.value)} 일정 추가"
            }
            startDate.value != null && endDate.value != null -> {
                "${df.format(startDate.value)} ~ ${df.format(endDate.value)} 일정 추가"
            }
            else -> ""
        }
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

    fun onClickClearPlan() {
        _startDate.value = null
        _endDate.value = null
        _state.value = CalendarState.OnClickDay
    }

    fun onClickAdd() {
    }
}

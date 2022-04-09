package com.imaec.triplan.ui.plan.more

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imaec.domain.Result
import com.imaec.domain.model.PlanDto
import com.imaec.domain.usecase.plan.GetPlanListUseCase
import com.imaec.triplan.ext.DATE_PATTERN_yyyy_MM_dd_E
import com.imaec.triplan.ext.dateToStringFormat
import com.imaec.triplan.ui.plan.PlanType
import com.imaec.triplan.ui.plan.more.PlanMoreActivity.Companion.PLAN_TYPE
import com.imaec.triplan.ui.plan.more.PlanMoreActivity.Companion.TITLE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class PlanMoreViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPlanListUseCase: GetPlanListUseCase
) : ViewModel() {

    private val _state = MutableLiveData<PlanMoreState>()
    val state: LiveData<PlanMoreState> get() = _state

    private val _title = MutableLiveData(savedStateHandle.get(TITLE) ?: "일정 전체보기")
    val title: LiveData<String> get() = _title

    private val _planList = MutableLiveData<List<PlanDto>>()
    val planList: LiveData<List<PlanDto>> get() = _planList

    private val planType = savedStateHandle.get<PlanType>(PLAN_TYPE)

    fun dateToString(date: Long): String = date.dateToStringFormat(DATE_PATTERN_yyyy_MM_dd_E)

    fun fetchData() {
        viewModelScope.launch {
            getPlanListUseCase().collect {
                when (it) {
                    is Result.Success -> {
                        _planList.value = if (planType == PlanType.PAST) {
                            it.data.sortedByDescending { it.startDate }
                        } else {
                            it.data.sortedBy { it.startDate }
                        }.run {
                            filter {
                                val today = LocalDate.now()
                                val startDate = LocalDate.ofEpochDay(it.startDate)
                                val endDate = LocalDate.ofEpochDay(it.endDate)
                                when (planType) {
                                    PlanType.UPCOMING -> startDate.isAfter(today)
                                    PlanType.PAST -> endDate.isBefore(today)
                                    else -> false
                                }
                            }
                        }
                    }
                    else -> {
                    }
                }
            }
        }
    }

    fun onClickPlan(plan: PlanDto) {
        _state.value = PlanMoreState.OnClickPlan(plan)
    }
}

package com.imaec.triplan.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imaec.domain.Result
import com.imaec.domain.model.PlanDto
import com.imaec.domain.usecase.plan.GetPlanListUseCase
import com.imaec.triplan.ext.DATE_PATTERN_yyyy_MM_dd_E
import com.imaec.triplan.ext.dateToStringFormat
import com.imaec.triplan.ui.plan.PlanType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPlanListUseCase: GetPlanListUseCase
) : ViewModel() {

    private val _state = MutableLiveData<HomeState>()
    val state: LiveData<HomeState> get() = _state

    private val _homeList = MutableLiveData<List<HomeItem>>()
    val homeList: LiveData<List<HomeItem>> get() = _homeList

    fun getDateString(startDate: Long, endDate: Long): String =
        if (startDate == endDate) {
            startDate.dateToStringFormat(DATE_PATTERN_yyyy_MM_dd_E)
        } else {
            "${startDate.dateToStringFormat(DATE_PATTERN_yyyy_MM_dd_E)} ~ " +
                endDate.dateToStringFormat(DATE_PATTERN_yyyy_MM_dd_E)
        }

    fun fetchData() {
        viewModelScope.launch {
            getPlanListUseCase().collect {
                when (it) {
                    is Result.Success -> {
                        it.data.groupBy {
                            val today = LocalDate.now()
                            val startDate = LocalDate.ofEpochDay(it.startDate)
                            val endDate = LocalDate.ofEpochDay(it.endDate)
                            when {
                                startDate == today ||
                                    endDate == today ||
                                    (startDate.isBefore(today) && endDate.isAfter(today)) -> {
                                    // 현재 일정
                                    PlanType.CURRENT
                                }
                                startDate.isAfter(today) -> {
                                    // 다가오는 일정
                                    PlanType.UPCOMING
                                }
                                else -> {
                                    // 다녀온 일정, it.endDate.isBefore(today)
                                    PlanType.PAST
                                }
                            }
                        }.let { result ->
                            val tempList = mutableListOf<HomeItem>()
                            result.keys.sortedBy {
                                it.ordinal
                            }.forEach {
                                if (tempList.isNotEmpty()) tempList.add(HomeItem.Divider)
                                result[it]?.let { list ->
                                    if (list.isNotEmpty()) {
                                        tempList.add(
                                            HomeItem.Plan(
                                                it,
                                                if (it == PlanType.PAST) {
                                                    list.sortedByDescending { it.startDate }
                                                } else {
                                                    list.sortedBy { it.startDate }
                                                }.take(2)
                                            )
                                        )
                                    }
                                }
                            }

                            _homeList.value = tempList
                        }
                    }
                    Result.Empty -> _homeList.value = emptyList()
                    else -> {
                    }
                }
            }
        }
    }

    fun onClickWrite() {
        _state.value = HomeState.OnClickWrite
    }

    fun onClickPlan(plan: PlanDto) {
        _state.value = HomeState.OnClickPlan(plan)
    }

    fun onClickMore(planType: PlanType) {
        _state.value = HomeState.OnClickMore(planType)
    }
}

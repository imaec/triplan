package com.imaec.triplan.ui.plan.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imaec.domain.Result
import com.imaec.domain.model.PlanDayDto
import com.imaec.domain.model.PlanDto
import com.imaec.domain.model.PlanItemDto
import com.imaec.domain.usecase.plan.GetPlanUseCase
import com.imaec.triplan.ext.DATE_PATTERN_MM_DD_E
import com.imaec.triplan.ext.dateToStringFormat
import com.imaec.triplan.ui.plan.list.PlanListFragment.Companion.PLAN
import com.imaec.triplan.ui.plan.list.PlanListFragment.Companion.PLAN_DAY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPlanUseCase: GetPlanUseCase
) : ViewModel() {

    private val _state = MutableLiveData<PlanListState>()
    val state: LiveData<PlanListState> get() = _state

    private val _planDay = MutableLiveData<PlanDayDto>(savedStateHandle.get(PLAN_DAY))
    val planDay: LiveData<PlanDayDto> get() = _planDay

    private val _planItemList = MutableLiveData<List<PlanListItem>>()
    val planItemList: LiveData<List<PlanListItem>> get() = _planItemList

    var plan = savedStateHandle.get<PlanDto>(PLAN)

    fun fetchData() {
        viewModelScope.launch {
            getPlanUseCase(plan?.planId ?: 0).collect {
                when (it) {
                    is Result.Success -> {
                        _planDay.value = it.data.planDayList.filter {
                            it.planDay == planDay.value?.planDay
                        }.first()
                        plan = it.data
                        fetchPlanItemList()
                    }
                    else -> {
                    }
                }
            }
        }
    }

    private fun fetchPlanItemList() {
        val tempList = mutableListOf<PlanListItem>()
        planDay.value?.planItemList?.forEachIndexed { index, planItemDto ->
            tempList.add(
                PlanListItem.PlanItem(
                    index + 1,
                    planItemDto.copy(
                        lastItem = index == (planDay.value?.planItemList?.size ?: 0) - 1
                    )
                )
            )
        }
        tempList.add(PlanListItem.AddButton)

        _planItemList.value = tempList
    }

    fun title(): String = "DAY ${planDay.value?.planDay} - " +
        planDay.value?.planDate?.dateToStringFormat(DATE_PATTERN_MM_DD_E)

    fun onClickAdd() {
        _state.value = PlanListState.OnClickAdd
    }

    fun onClickPlanItem(planItem: PlanItemDto) {
        _state.value = PlanListState.OnClickPlanItem(planItem)
    }
}

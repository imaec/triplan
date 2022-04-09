package com.imaec.triplan.ui.plan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imaec.domain.model.PlanDto
import com.imaec.domain.usecase.plan.DeletePlanUseCase
import com.imaec.triplan.ext.DATE_PATTERN_yyyy_MM_dd_E
import com.imaec.triplan.ext.dateToStringFormat
import com.imaec.triplan.ui.plan.PlanDetailActivity.Companion.PLAN
import com.imaec.triplan.ui.plan.list.PlanListPageType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val deletePlanUseCase: DeletePlanUseCase
) : ViewModel() {

    private val _state = MutableLiveData<PlanDetailState>()
    val state: LiveData<PlanDetailState> get() = _state

    private val _pageType = MutableLiveData(PlanListPageType.FIRST_PAGE)
    val pageType: LiveData<PlanListPageType> get() = _pageType

    val plan = savedStateHandle.get<PlanDto>(PLAN)

    fun getDateString(startDate: Long, endDate: Long): String =
        if (startDate == endDate) {
            startDate.dateToStringFormat(DATE_PATTERN_yyyy_MM_dd_E)
        } else {
            "${startDate.dateToStringFormat(DATE_PATTERN_yyyy_MM_dd_E)} ~ " +
                endDate.dateToStringFormat(DATE_PATTERN_yyyy_MM_dd_E)
        }

    fun setCurrentPage(currentPage: Int) {
        plan ?: return
        _pageType.value = when (currentPage) {
            0 -> PlanListPageType.FIRST_PAGE
            plan.planDayList.size - 1 -> PlanListPageType.END_PAGE
            else -> PlanListPageType.MIDDLE_PAGE
        }
    }

    fun deletePlan() {
        plan ?: return

        viewModelScope.launch {
            deletePlanUseCase(plan)
            _state.value = PlanDetailState.DeletedPlan
        }
    }

    fun onClickLeft() {
        _state.value = PlanDetailState.OnClickLeft
    }

    fun onClickRight() {
        _state.value = PlanDetailState.OnClickRight
    }
}

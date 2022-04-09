package com.imaec.triplan.ui.plan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.imaec.domain.model.PlanDto
import com.imaec.triplan.ext.DATE_PATTERN_yyyy_MM_dd_E
import com.imaec.triplan.ext.dateToStringFormat
import com.imaec.triplan.ui.plan.PlanDetailActivity.Companion.PLAN
import com.imaec.triplan.ui.plan.list.PlanListPageType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlanDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableLiveData<PlanDetailState>()
    val state: LiveData<PlanDetailState> get() = _state

    private val _pageType = MutableLiveData(PlanListPageType.FIRST_PAGE)
    val pageType: LiveData<PlanListPageType> get() = _pageType

    val plan = savedStateHandle.get<PlanDto>(PLAN)

    fun dateToString(date: Long): String = date.dateToStringFormat(DATE_PATTERN_yyyy_MM_dd_E)

    fun setCurrentPage(currentPage: Int) {
        plan ?: return
        _pageType.value = when (currentPage) {
            0 -> PlanListPageType.FIRST_PAGE
            plan.planDayList.size - 1 -> PlanListPageType.END_PAGE
            else -> PlanListPageType.MIDDLE_PAGE
        }
    }

    fun onClickLeft() {
        _state.value = PlanDetailState.OnClickLeft
    }

    fun onClickRight() {
        _state.value = PlanDetailState.OnClickRight
    }
}

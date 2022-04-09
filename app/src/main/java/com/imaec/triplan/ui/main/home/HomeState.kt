package com.imaec.triplan.ui.main.home

import com.imaec.domain.model.PlanDto

sealed class HomeState {

    object OnClickWrite : HomeState()
    data class OnClickPlan(val plan: PlanDto) : HomeState()
}

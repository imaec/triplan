package com.imaec.triplan.ui.main.home

import com.imaec.domain.model.PlanDto
import com.imaec.triplan.ui.plan.PlanType

sealed class HomeState {

    object OnClickWrite : HomeState()
    data class OnClickPlan(val plan: PlanDto) : HomeState()
    data class OnClickMore(val planType: PlanType) : HomeState()
}

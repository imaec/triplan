package com.imaec.triplan.ui.plan.more

import com.imaec.domain.model.PlanDto

sealed class PlanMoreState {

    data class OnClickPlan(val plan: PlanDto) : PlanMoreState()
}

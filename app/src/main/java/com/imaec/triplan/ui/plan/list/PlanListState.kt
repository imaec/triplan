package com.imaec.triplan.ui.plan.list

import com.imaec.domain.model.PlanItemDto

sealed class PlanListState {

    data class OnClickPlanItem(val planItem: PlanItemDto) : PlanListState()
    object OnClickAdd : PlanListState()
}

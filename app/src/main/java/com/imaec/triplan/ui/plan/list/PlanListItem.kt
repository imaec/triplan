package com.imaec.triplan.ui.plan.list

import com.imaec.domain.model.PlanItemDto

sealed class PlanListItem {

    data class PlanItem(val itemNo: Int, val planItem: PlanItemDto) : PlanListItem()
    object AddButton : PlanListItem()
}

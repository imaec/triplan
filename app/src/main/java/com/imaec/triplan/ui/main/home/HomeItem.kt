package com.imaec.triplan.ui.main.home

import com.imaec.domain.model.PlanDto
import com.imaec.triplan.ui.plan.PlanType

sealed class HomeItem {
    data class Plan(val planType: PlanType, val list: List<PlanDto>) : HomeItem()
    object Divider : HomeItem()
}

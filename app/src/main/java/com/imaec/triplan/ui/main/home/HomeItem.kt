package com.imaec.triplan.ui.main.home

import com.imaec.domain.model.PlanDto

sealed class HomeItem {
    data class Plan(val planType: HomePlanType, val list: List<PlanDto>) : HomeItem()
    object Divider : HomeItem()
}

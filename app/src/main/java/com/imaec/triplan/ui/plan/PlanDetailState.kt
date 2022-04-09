package com.imaec.triplan.ui.plan

sealed class PlanDetailState {

    object OnClickLeft : PlanDetailState()
    object OnClickRight : PlanDetailState()
}

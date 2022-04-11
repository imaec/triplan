package com.imaec.triplan.ui.plan.write

sealed class PlanWriteState {

    object OnClickCategory : PlanWriteState()
    object OnClickAddCategory : PlanWriteState()
    object OnClickCity : PlanWriteState()
    object OnClickAddCity : PlanWriteState()
    object OnClickPlace : PlanWriteState()
    object OnClickAddPlace : PlanWriteState()
    object OnClickAddress : PlanWriteState()
    object OnClickSave : PlanWriteState()
    data class OnError(val message: String) : PlanWriteState()
}

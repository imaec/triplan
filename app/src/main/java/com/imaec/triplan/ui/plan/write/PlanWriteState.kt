package com.imaec.triplan.ui.plan.write

import com.imaec.domain.model.NaverPlaceDto

sealed class PlanWriteState {

    object OnClickCategory : PlanWriteState()
    object OnClickAddCategory : PlanWriteState()
    object OnClickCity : PlanWriteState()
    object OnClickAddCity : PlanWriteState()
    data class OnLoadNaverPlace(val list: List<NaverPlaceDto>) : PlanWriteState()
    object OnClickAddress : PlanWriteState()
    object OnClickSave : PlanWriteState()
    data class OnError(val message: String) : PlanWriteState()
}

package com.imaec.triplan.ui.main.search

import com.imaec.domain.model.PlaceDto
import com.imaec.domain.model.PlanDto

sealed class SearchState {

    object OnClickSearch : SearchState()
    data class OnClickPlan(val plan: PlanDto) : SearchState()
    data class OnClickPlanMore(val keyword: String?) : SearchState()
    data class OnClickPlace(val place: PlaceDto) : SearchState()
    data class OnClickPlaceMore(val keyword: String?) : SearchState()
}

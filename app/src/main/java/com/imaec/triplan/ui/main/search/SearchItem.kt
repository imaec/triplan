package com.imaec.triplan.ui.main.search

import com.imaec.domain.model.PlaceDto
import com.imaec.domain.model.PlanDto

sealed class SearchItem {

    data class SearchResultPlan(val list: List<PlanDto>) : SearchItem()
    data class SearchResultPlace(val list: List<PlaceDto>) : SearchItem()
}

package com.imaec.triplan.ui.writeplace

import com.imaec.domain.model.NaverPlaceDto
import com.imaec.domain.model.PlaceDto

sealed class WritePlaceState {

    object OnClickCategory : WritePlaceState()
    object OnClickAddCategory : WritePlaceState()
    object OnClickCity : WritePlaceState()
    object OnClickAddCity : WritePlaceState()
    object OnClickAddress : WritePlaceState()
    data class OnLoadNaverPlace(val list: List<NaverPlaceDto>) : WritePlaceState()
    data class OnSuccess(val place: PlaceDto) : WritePlaceState()
    data class OnError(val message: String) : WritePlaceState()
}

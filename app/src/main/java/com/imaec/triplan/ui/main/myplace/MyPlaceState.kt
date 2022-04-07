package com.imaec.triplan.ui.main.myplace

import com.imaec.domain.model.PlaceDto

sealed class MyPlaceState {

    object OnClickWrite : MyPlaceState()
    data class OnClickPlace(val place: PlaceDto) : MyPlaceState()
    data class OnLongClickPlace(val place: PlaceDto) : MyPlaceState()
    data class DeletedPlace(val message: String) : MyPlaceState()
}

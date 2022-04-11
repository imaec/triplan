package com.imaec.triplan.ui.select.place

import com.imaec.domain.model.PlaceDto

sealed class SelectPlaceState {

    data class OnClickPlace(val place: PlaceDto) : SelectPlaceState()
    object OnClickAddPlace : SelectPlaceState()
}

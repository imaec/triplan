package com.imaec.triplan.ui.place

import com.imaec.domain.model.PlaceDto

sealed class PlaceMoreState {

    data class OnClickPlace(val place: PlaceDto) : PlaceMoreState()
    data class OnLongClickPlace(val place: PlaceDto) : PlaceMoreState()
    data class DeletedPlace(val message: String) : PlaceMoreState()
}

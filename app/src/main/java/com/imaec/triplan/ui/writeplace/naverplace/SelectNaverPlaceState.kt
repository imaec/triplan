package com.imaec.triplan.ui.writeplace.naverplace

import com.imaec.domain.model.NaverPlaceDto

sealed class SelectNaverPlaceState {

    data class OnClickPlace(val place: NaverPlaceDto) : SelectNaverPlaceState()
}

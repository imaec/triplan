package com.imaec.triplan.ui.writeplace

import com.imaec.domain.model.NaverPlaceDto

sealed class WritePlaceState {

    object OnClickCategory : WritePlaceState()
    object OnClickAddCategory : WritePlaceState()
    object OnClickCity : WritePlaceState()
    object OnClickAddCity : WritePlaceState()
    object OnClickAddress : WritePlaceState()
    data class OnLoadNaverPlace(val list: List<NaverPlaceDto>) : WritePlaceState()
    object OnSuccess : WritePlaceState()
    data class OnError(val message: String) : WritePlaceState()
}

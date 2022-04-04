package com.imaec.triplan.ui.writeplace

sealed class WritePlaceState {

    object OnClickCategory : WritePlaceState()
    object OnClickAddCategory : WritePlaceState()
    object OnClickCity : WritePlaceState()
    object OnClickAddCity : WritePlaceState()
    object OnClickAddress : WritePlaceState()
}

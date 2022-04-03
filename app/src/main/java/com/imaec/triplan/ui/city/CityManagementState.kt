package com.imaec.triplan.ui.city

import com.imaec.domain.model.CityDto

sealed class CityManagementState {

    data class OnError(val message: String) : CityManagementState()
    object OnClickAdd : CityManagementState()
    data class OnClickCity(val city: CityDto) : CityManagementState()
}

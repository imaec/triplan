package com.imaec.triplan.ui.writeplace.city

import com.imaec.domain.model.CityDto

sealed class SelectCityState {

    data class OnClickCity(val city: CityDto) : SelectCityState()
}

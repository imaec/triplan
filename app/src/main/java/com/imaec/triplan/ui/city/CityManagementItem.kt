package com.imaec.triplan.ui.city

import com.imaec.domain.model.CityDto

sealed class CityManagementItem {
    object CityInput : CityManagementItem()
    data class City(val city: CityDto) : CityManagementItem()
}

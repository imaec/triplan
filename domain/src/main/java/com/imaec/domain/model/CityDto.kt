package com.imaec.domain.model

data class CityDto(
    val cityId: Long,
    val city: String,
    val defaultCity: Boolean = false
)

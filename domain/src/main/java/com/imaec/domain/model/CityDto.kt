package com.imaec.domain.model

import java.io.Serializable

data class CityDto(
    val cityId: Long,
    val city: String,
    val defaultCity: Boolean = false
) : Serializable

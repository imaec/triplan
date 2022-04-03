package com.imaec.triplan.model

data class CityVo(
    val cityId: Long,
    val city: String,
    val defaultCity: Boolean = false
)

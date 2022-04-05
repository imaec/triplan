package com.imaec.domain.model

import java.text.SimpleDateFormat
import java.util.Date

data class PlaceDto(
    val placeId: Long = 0,
    val categoryId: Long,
    val cityId: Long,
    val placeName: String,
    val address: String = "",
    val siteUrl: String = "",
    val imageUrl: String = "",
    val saveTime: String = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
)

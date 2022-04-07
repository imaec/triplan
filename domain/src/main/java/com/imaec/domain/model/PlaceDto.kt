package com.imaec.domain.model

import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Date

data class PlaceDto(
    val placeId: Long = 0,
    val categoryId: Long,
    val category: String,
    val cityId: Long,
    val city: String,
    val placeName: String,
    val address: String = "",
    val siteUrl: String = "",
    val imageUrl: String = "",
    val saveTime: String = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
) : Serializable

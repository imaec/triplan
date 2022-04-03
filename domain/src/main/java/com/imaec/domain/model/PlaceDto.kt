package com.imaec.domain.model

data class PlaceDto(
    val placeId: Long,
    val categoryId: Long,
    val cityId: Long,
    val placeName: String,
    val address: String,
    val siteUrl: String,
    val imageUrl: String,
    val saveTime: String
)

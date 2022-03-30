package com.imaec.triplan.model

data class PlaceVo(
    val placeId: Long = 0,
    val placeName: String,
    val address: String,
    val siteUrl: String,
    val imageUrl: String,
    val saveTime: String
)

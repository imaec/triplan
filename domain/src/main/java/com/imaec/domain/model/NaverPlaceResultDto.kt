package com.imaec.domain.model

import java.io.Serializable

data class NaverPlaceResultDto(
    val lastBuildDate: String,
    val total: Int,
    val start: Int,
    val display: Int,
    val items: List<NaverPlaceDto>
)

data class NaverPlaceDto(
    val title: String,
    val link: String,
    val category: String,
    val description: String,
    val telephone: String,
    val address: String?,
    val roadAddress: String?,
    val mapx: String,
    val mapy: String
) : Serializable

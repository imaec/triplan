package com.imaec.data.entity

import com.imaec.domain.model.NaverPlaceDto
import com.imaec.domain.model.NaverPlaceResultDto

data class NaverPlaceResultEntity(
    val lastBuildDate: String,
    val total: Int,
    val start: Int,
    val display: Int,
    val items: List<NaverPlaceEntity>
) {
    fun toDto() = NaverPlaceResultDto(
        lastBuildDate = lastBuildDate,
        total = total,
        start = start,
        display = display,
        items = items.map { it.toDto() }
    )
}

data class NaverPlaceEntity(
    val title: String?,
    val link: String?,
    val category: String?,
    val description: String?,
    val telephone: String?,
    val address: String?,
    val roadAddress: String?,
    val mapx: String?,
    val mapy: String?
) {
    fun toDto() = NaverPlaceDto(
        title = title ?: "",
        link = link ?: "",
        category = category ?: "",
        description = description ?: "",
        telephone = telephone ?: "",
        address = address,
        roadAddress = roadAddress,
        mapx = mapx ?: "",
        mapy = mapy ?: ""
    )
}

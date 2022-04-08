package com.imaec.domain.model

data class PlanDto(
    val planId: Long,
    val planName: String,
    val planItemList: List<PlanItemDto> = emptyList(),
    val city: String,
    val startDate: String,
    val endDate: String
)

data class PlanItemDto(
    val planItemId: Long,
    val date: String,
    val placeName: String,
    val city: String,
    val address: String,
    val time: String,
    val description: String,
    val cost: String
)

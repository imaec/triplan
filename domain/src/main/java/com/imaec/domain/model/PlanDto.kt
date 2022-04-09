package com.imaec.domain.model

import java.io.Serializable

data class PlanDto(
    val planId: Long = 0,
    val planName: String,
    val planDayList: List<PlanDayDto> = emptyList(),
    val city: String,
    val startDate: Long,
    val endDate: Long
) : Serializable

data class PlanDayDto(
    val planDay: Int,
    val planDate: Long,
    val planItemList: List<PlanItemDto>
) : Serializable

data class PlanItemDto(
    val itemNo: Int,
    val placeName: String,
    val city: String,
    val category: String,
    val address: String,
    val time: String,
    val description: String,
    val cost: String,
    val lastItem: Boolean = false
) : Serializable

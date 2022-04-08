package com.imaec.domain.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class PlanDto(
    val planId: Long = 0,
    val planName: String,
    val planItemList: List<PlanItemDto> = emptyList(),
    val city: String,
    val startDate: LocalDate,
    val endDate: LocalDate
) {
    private val df = DateTimeFormatter.ofPattern("yyyy.MM.dd(E)")
    fun startDateString() = df.format(startDate)
    fun endDateString() = df.format(endDate)
}

data class PlanItemDto(
    val planItemId: Long = 0,
    val date: String,
    val placeName: String,
    val city: String,
    val address: String,
    val time: String,
    val description: String,
    val cost: String
)

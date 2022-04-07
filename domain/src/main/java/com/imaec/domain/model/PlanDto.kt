package com.imaec.domain.model

data class PlanDto(
    val planId: Long,
    val planName: String,
    val city: String,
    val startDate: String,
    val endDate: String
)

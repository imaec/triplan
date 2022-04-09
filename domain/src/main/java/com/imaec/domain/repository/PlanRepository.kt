package com.imaec.domain.repository

import com.imaec.domain.Result
import com.imaec.domain.model.PlanDto
import kotlinx.coroutines.flow.Flow

interface PlanRepository {

    suspend fun addPlan(plan: PlanDto): PlanDto

    fun getPlanList(): Flow<Result<List<PlanDto>>>

    fun getPlan(planId: Long): Flow<Result<PlanDto>>

    fun searchPlanList(keyword: String, moreResult: Boolean): List<PlanDto>

    suspend fun updatePlan(plan: PlanDto)

    suspend fun deletePlan(plan: PlanDto)
}

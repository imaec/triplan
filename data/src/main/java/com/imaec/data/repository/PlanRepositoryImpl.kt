package com.imaec.data.repository

import android.util.Log
import com.imaec.data.db.dao.PlanDao
import com.imaec.data.entity.local.PlanEntity.Companion.fromDto
import com.imaec.data.entity.local.PlanEntity.Companion.toDto
import com.imaec.domain.Result
import com.imaec.domain.model.PlanDto
import com.imaec.domain.repository.PlanRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import java.lang.Exception

class PlanRepositoryImpl(
    private val dao: PlanDao
) : PlanRepository {

    override suspend fun addPlan(plan: PlanDto): PlanDto {
        val entity = fromDto(plan)
        val result = dao.insert(entity)
        return plan.copy(planId = result)
    }

    override fun getPlanList(): Flow<Result<List<PlanDto>>> = flow {
        emit(Result.Loading)
        dao.getPlanList().collect {
            if (it.isEmpty()) {
                emit(Result.Empty)
            } else {
                emit(Result.Success(it.map(::toDto)))
            }
        }
    }.catch { e ->
        Timber.e("  ## error : ${Log.getStackTraceString(e)}")
        Result.Error(Exception(e))
    }.flowOn(Dispatchers.IO)

    override fun getPlan(planId: Long) = flow {
        emit(Result.Loading)
        dao.getPlan(planId).collect {
            if (it == null) {
                emit(Result.Error(Exception("not exist plan")))
            } else {
                emit(Result.Success(toDto(it)))
            }
        }
    }.catch { e ->
        Timber.e("  ## error : ${Log.getStackTraceString(e)}")
        Result.Error(Exception(e))
    }.flowOn(Dispatchers.IO)

    override fun searchPlanList(keyword: String, moreResult: Boolean) =
        if (moreResult) dao.searchPlanList(keyword).map(::toDto)
        else dao.searchPlanListLimit(keyword, 2).map(::toDto)

    override suspend fun updatePlan(plan: PlanDto) {
        dao.update(fromDto(plan))
    }

    override suspend fun deletePlan(plan: PlanDto) {
        dao.delete(fromDto(plan))
    }
}

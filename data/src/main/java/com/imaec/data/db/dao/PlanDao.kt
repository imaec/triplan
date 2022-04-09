package com.imaec.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.imaec.data.entity.local.PlanEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlanDao : BaseDao<PlanEntity> {

    @Query("SELECT * FROM planEntity")
    fun getPlanList(): Flow<List<PlanEntity>>

    @Query("SELECT * FROM planEntity WHERE planId = :planId")
    fun getPlan(planId: Long): Flow<PlanEntity?>

    @Query(
        "SELECT * FROM planEntity WHERE planName LIKE :keyword OR city LIKE :keyword"
    )
    fun searchPlanList(keyword: String): List<PlanEntity>

    @Query(
        "SELECT * FROM planEntity WHERE planName LIKE :keyword OR city LIKE :keyword " +
            "LIMIT :limit"
    )
    fun searchPlanListLimit(keyword: String, limit: Int): List<PlanEntity>
}

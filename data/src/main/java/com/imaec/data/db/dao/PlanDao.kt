package com.imaec.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.imaec.data.entity.local.PlanEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlanDao : BaseDao<PlanEntity> {

    @Query(value = "SELECT * FROM planEntity")
    fun getPlanList(): Flow<List<PlanEntity>>
}

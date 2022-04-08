package com.imaec.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.imaec.data.entity.local.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao : BaseDao<CategoryEntity> {

    @Query("SELECT * FROM categoryEntity")
    fun getCategoryList(): Flow<List<CategoryEntity>>

    @Query("SELECT COUNT(*) FROM categoryEntity WHERE :category = category")
    fun getCountByCategory(category: String): Int
}

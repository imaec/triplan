package com.imaec.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.imaec.data.entity.local.RecentKeywordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentKeywordDao : BaseDao<RecentKeywordEntity> {

    @Query("SELECT keyword FROM recentKeywordEntity ORDER BY updated DESC LIMIT $MAX_COUNT")
    fun getRecentKeywordList(): Flow<List<String>>

    @Query("DELETE FROM recentKeywordEntity WHERE keyword = :keyword")
    suspend fun deleteKeyword(keyword: String)

    @Query("DELETE FROM recentKeywordEntity")
    suspend fun deleteAll()

    @Query(
        "DELETE FROM recentKeywordEntity WHERE keyword NOT IN(" +
            "SELECT keyword FROM recentKeywordEntity ORDER BY updated DESC LIMIT $MAX_COUNT" +
            ")"
    )
    suspend fun deleteCountLimit()

    companion object {
        const val MAX_COUNT = 30
    }
}

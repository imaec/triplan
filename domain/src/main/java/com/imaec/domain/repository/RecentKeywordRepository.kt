package com.imaec.domain.repository

import com.imaec.domain.Result
import kotlinx.coroutines.flow.Flow

interface RecentKeywordRepository {

    fun getRecentKeywordList(): Flow<Result<List<String>>>

    suspend fun saveKeyword(keyword: String)

    suspend fun deleteKeyword(keyword: String)
}

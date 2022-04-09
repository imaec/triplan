package com.imaec.data.repository

import com.imaec.data.db.dao.RecentKeywordDao
import com.imaec.data.entity.local.RecentKeywordEntity
import com.imaec.domain.Result
import com.imaec.domain.repository.RecentKeywordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import java.util.Date

class RecentKeywordRepositoryImpl(
    private val dao: RecentKeywordDao
) : RecentKeywordRepository {

    override fun getRecentKeywordList() = flow {
        emit(Result.Loading)
        dao.getRecentKeywordList().collect {
            if (it.isEmpty()) {
                emit(Result.Empty)
            } else {
                emit(Result.Success(it))
            }
        }
    }.catch { e ->
        Result.Error(Exception(e))
    }.flowOn(Dispatchers.IO)

    override suspend fun saveKeyword(keyword: String) {
        dao.insert(
            RecentKeywordEntity(
                keyword = keyword,
                updated = Date()
            )
        )
        // 최대 개수가 넘어가면 오래된순으로 삭제
        dao.deleteCountLimit()
    }

    override suspend fun deleteKeyword(keyword: String) {
        dao.deleteKeyword(keyword)
    }
}

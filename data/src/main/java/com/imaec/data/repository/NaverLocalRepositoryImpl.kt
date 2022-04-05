package com.imaec.data.repository

import com.imaec.data.api.KEY_DISPLAY
import com.imaec.data.api.KEY_QUERY
import com.imaec.data.api.NaverLocalService
import com.imaec.data.api.VALUE_DISPLAY
import com.imaec.domain.Result
import com.imaec.domain.model.NaverPlaceDto
import com.imaec.domain.repository.NaverLocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class NaverLocalRepositoryImpl(
    private val service: NaverLocalService
) : NaverLocalRepository {

    override suspend fun getNaverPlace(query: String): Flow<Result<List<NaverPlaceDto>>> = flow {
        emit(Result.Loading)
        val result = service.getPlace(
            mapOf(
                KEY_QUERY to query,
                KEY_DISPLAY to VALUE_DISPLAY
            )
        ).toDto()

        if (result.total == 0) {
            emit(Result.Empty)
        } else {
            emit(Result.Success(result.items))
        }
    }.catch { e ->
        Result.Error(Exception(e))
    }.flowOn(Dispatchers.IO)
}

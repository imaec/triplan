package com.imaec.data.repository

import com.imaec.data.api.RoadAddressService
import com.imaec.domain.Result
import com.imaec.domain.model.RoadAddressDto
import com.imaec.domain.repository.RoadAddressRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class RoadAddressRepositoryImpl(
    private val service: RoadAddressService
) : RoadAddressRepository {

    override suspend fun getAddress(keyword: String): Flow<Result<List<RoadAddressDto>>> = flow {
        emit(Result.Loading)
        val result = service.getAddress(
            mapOf(
                "confmKey" to "U01TX0FVVEgyMDIyMDQwNDE1MjI1MjExMjQxNzU=",
                "currentPage" to "1",
                "countPerPage" to "30",
                "keyword" to keyword,
                "resultType" to "json"
            )
        ).toDto()

        if (result.results.common.errorCode == "0") {
            if (result.results.juso.isNullOrEmpty()) {
                emit(Result.Empty)
            } else {
                emit(Result.Success(result.results.juso!!))
            }
        } else {
            emit(Result.Error(Exception(result.results.common.errorMessage)))
        }
    }.catch { e ->
        Result.Error(Exception(e))
    }.flowOn(Dispatchers.IO)
}

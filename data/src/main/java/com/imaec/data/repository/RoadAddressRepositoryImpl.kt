package com.imaec.data.repository

import com.imaec.data.api.KEY_CONFM_KEY
import com.imaec.data.api.KEY_COUNT_PER_PAGE
import com.imaec.data.api.KEY_CURRENT_PAGE
import com.imaec.data.api.KEY_KEYWORD
import com.imaec.data.api.KEY_RESULT_TYPE
import com.imaec.data.api.NO_ERROR
import com.imaec.data.api.RoadAddressService
import com.imaec.data.api.VALUE_CONFM_KEY
import com.imaec.data.api.VALUE_COUNT_PER_PAGE
import com.imaec.data.api.VALUE_CURRENT_PAGE
import com.imaec.data.api.VALUE_RESULT_TYPE
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
                KEY_CONFM_KEY to VALUE_CONFM_KEY,
                KEY_CURRENT_PAGE to VALUE_CURRENT_PAGE,
                KEY_COUNT_PER_PAGE to VALUE_COUNT_PER_PAGE,
                KEY_KEYWORD to keyword,
                KEY_RESULT_TYPE to VALUE_RESULT_TYPE
            )
        ).toDto()

        if (result.results.common.errorCode == NO_ERROR) {
            if (result.results.juso.isNullOrEmpty()) {
                emit(Result.Empty)
            } else {
                emit(Result.Success(result.results.juso!!))
            }
        }
    }.catch { e ->
        Result.Error(Exception(e))
    }.flowOn(Dispatchers.IO)
}

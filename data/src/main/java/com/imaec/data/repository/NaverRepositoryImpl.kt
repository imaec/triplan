package com.imaec.data.repository

import com.imaec.data.api.KEY_COUNT
import com.imaec.data.api.KEY_QUERY
import com.imaec.data.api.NaverService
import com.imaec.data.api.STATUS_OK
import com.imaec.data.api.VALUE_COUNT
import com.imaec.domain.Result
import com.imaec.domain.model.AddressDto
import com.imaec.domain.repository.NaverRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class NaverRepositoryImpl(
    private val service: NaverService
) : NaverRepository {

    override suspend fun getAddress(query: String): Flow<Result<List<AddressDto>>> = flow {
        emit(Result.Loading)
        val result = service.getAddress(
            mapOf(
                KEY_QUERY to query,
                KEY_COUNT to VALUE_COUNT
            )
        ).toDto()
        if (result.status == STATUS_OK) {
            if (result.addresses.isEmpty()) {
                emit(Result.Empty)
            } else {
                emit(Result.Success(result.addresses))
            }
        } else {
            Result.Error(Exception(result.errorMessage))
        }
    }.catch { e ->
        Result.Error(Exception(e))
    }.flowOn(Dispatchers.IO)
}

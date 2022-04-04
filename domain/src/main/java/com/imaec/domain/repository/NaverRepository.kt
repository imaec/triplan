package com.imaec.domain.repository

import com.imaec.domain.Result
import com.imaec.domain.model.AddressDto
import kotlinx.coroutines.flow.Flow

interface NaverRepository {

    suspend fun getAddress(query: String): Flow<Result<List<AddressDto>>>
}

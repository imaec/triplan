package com.imaec.domain.repository

import com.imaec.domain.Result
import com.imaec.domain.model.RoadAddressDto
import kotlinx.coroutines.flow.Flow

interface RoadAddressRepository {

    suspend fun getAddress(keyword: String): Flow<Result<List<RoadAddressDto>>>
}

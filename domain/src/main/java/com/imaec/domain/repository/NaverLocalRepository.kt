package com.imaec.domain.repository

import com.imaec.domain.Result
import com.imaec.domain.model.NaverPlaceDto
import kotlinx.coroutines.flow.Flow

interface NaverLocalRepository {

    suspend fun getNaverPlace(query: String): Flow<Result<List<NaverPlaceDto>>>
}

package com.imaec.domain.repository

import com.imaec.domain.Result
import com.imaec.domain.model.PlaceDto
import kotlinx.coroutines.flow.Flow

interface PlaceRepository {

    fun getPlaceList(): Flow<Result<List<PlaceDto>>>

    suspend fun savePlace(place: PlaceDto): PlaceDto

    suspend fun editPlace(place: PlaceDto): PlaceDto

    suspend fun deletePlace(place: PlaceDto): PlaceDto
}

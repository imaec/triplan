package com.imaec.domain.repository

import com.imaec.domain.Result
import com.imaec.domain.model.PlaceDto
import kotlinx.coroutines.flow.Flow

interface PlaceRepository {

    fun getPlaceList(): Flow<Result<List<PlaceDto>>>

    fun getPlaceCountByCategory(category: String): Int

    fun getPlaceCountByCity(city: String): Int

    fun searchPlaceList(keyword: String, moreResult: Boolean): List<PlaceDto>

    suspend fun savePlace(place: PlaceDto): PlaceDto

    suspend fun editPlace(place: PlaceDto): PlaceDto

    suspend fun deletePlace(place: PlaceDto): PlaceDto
}

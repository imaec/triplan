package com.imaec.domain.repository

import com.imaec.domain.Result
import com.imaec.domain.model.CityDto
import kotlinx.coroutines.flow.Flow

interface CityRepository {

    suspend fun addCity(city: String): CityDto?

    suspend fun addAllCity(cityList: List<CityDto>)

    fun getCityList(): Flow<Result<List<CityDto>>>

    fun getCity(city: String): Flow<Result<CityDto>>

    suspend fun updateCity(city: CityDto)

    suspend fun deleteCity(city: CityDto)
}

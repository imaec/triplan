package com.imaec.data.repository

import com.imaec.data.db.dao.CityDao
import com.imaec.data.entity.local.CityEntity
import com.imaec.data.entity.local.CityEntity.Companion.fromDto
import com.imaec.data.entity.local.CityEntity.Companion.toDto
import com.imaec.domain.Result
import com.imaec.domain.model.CityDto
import com.imaec.domain.repository.CityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class CityRepositoryImpl(
    private val dao: CityDao
) : CityRepository {

    override suspend fun addCity(city: String): CityDto? {
        if (dao.getCountByCity(city) == 0) {
            val entity = CityEntity(city = city)
            val result = dao.insert(entity)
            return toDto(entity.copy(cityId = result))
        }
        return null
    }

    override suspend fun addAllCity(cityList: List<CityDto>) {
        dao.insertAll(cityList.map(::fromDto))
    }

    override fun getCityList() = flow {
        emit(Result.Loading)
        dao.getCityList().collect {
            if (it.isEmpty()) {
                emit(Result.Empty)
            } else {
                emit(Result.Success(it.map(::toDto)))
            }
        }
    }.catch { e ->
        Result.Error(Exception(e))
    }.flowOn(Dispatchers.IO)

    override fun getCity(city: String) = flow {
        emit(Result.Loading)
        dao.getCity(city).collect {
            emit(Result.Success(toDto(it)))
        }
    }.catch { e ->
        Result.Error(Exception(e))
    }.flowOn(Dispatchers.IO)

    override suspend fun updateCity(city: CityDto) {
        dao.update(fromDto(city))
    }

    override suspend fun deleteCity(city: CityDto) {
        dao.delete(fromDto(city))
    }
}

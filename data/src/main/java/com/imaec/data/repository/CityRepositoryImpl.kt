package com.imaec.data.repository

import com.imaec.data.db.dao.CityDao
import com.imaec.data.entity.CityEntity
import com.imaec.data.entity.CityEntity.Companion.fromDto
import com.imaec.data.entity.CityEntity.Companion.toDto
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

    override suspend fun addCity(city: String) {
        if (dao.getCountByCity(city) == 0) dao.insert(CityEntity(city = city))
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

    override suspend fun updateCity(city: CityDto) {
        dao.update(fromDto(city))
    }

    override suspend fun deleteCity(city: CityDto) {
        dao.delete(fromDto(city))
    }
}

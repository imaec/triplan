package com.imaec.data.repository

import com.imaec.data.db.dao.PlaceDao
import com.imaec.data.entity.local.PlaceEntity
import com.imaec.data.entity.local.PlaceEntity.Companion.toDto
import com.imaec.domain.Result
import com.imaec.domain.model.PlaceDto
import com.imaec.domain.repository.PlaceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class PlaceRepositoryImpl(
    private val dao: PlaceDao
) : PlaceRepository {

    override fun getPlaceList() = flow {
        emit(Result.Loading)
        dao.getPlaceList().collect {
            if (it.isEmpty()) {
                emit(Result.Empty)
            } else {
                emit(Result.Success(it.map(::toDto)))
            }
        }
    }.catch { e ->
        Result.Error(Exception(e))
    }.flowOn(Dispatchers.IO)

    override suspend fun savePlace(place: PlaceDto): PlaceDto {
        val entity = PlaceEntity(
            categoryId = place.categoryId,
            category = place.category,
            cityId = place.cityId,
            city = place.city,
            placeName = place.placeName,
            address = place.address,
            siteUrl = place.siteUrl,
            imageUrl = place.imageUrl,
            saveTime = place.saveTime
        )
        val result = dao.insert(entity)
        return toDto(entity.copy(placeId = result))
    }

    override suspend fun editPlace(place: PlaceDto): PlaceDto {
        val entity = PlaceEntity(
            placeId = place.placeId,
            categoryId = place.categoryId,
            category = place.category,
            cityId = place.cityId,
            city = place.city,
            placeName = place.placeName,
            address = place.address,
            siteUrl = place.siteUrl,
            imageUrl = place.imageUrl,
            saveTime = place.saveTime
        )
        dao.update(entity)
        return place
    }

    override suspend fun deletePlace(place: PlaceDto): PlaceDto {
        val entity = PlaceEntity(
            placeId = place.placeId,
            categoryId = place.categoryId,
            category = place.category,
            cityId = place.cityId,
            city = place.city,
            placeName = place.placeName,
            address = place.address,
            siteUrl = place.siteUrl,
            imageUrl = place.imageUrl,
            saveTime = place.saveTime
        )
        dao.delete(entity)
        return place
    }
}

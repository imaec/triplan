package com.imaec.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.imaec.data.entity.local.PlaceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDao : BaseDao<PlaceEntity> {

    @Query(
        value = "SELECT * FROM placeEntity AS place " +
            "INNER JOIN categoryEntity ON place.categoryId = categoryEntity.categoryId " +
            "INNER JOIN cityEntity ON place.cityId = cityEntity.cityId"
    )
    fun getPlaceList(): Flow<List<PlaceEntity>>

    @Query(
        value = "SELECT * FROM placeEntity AS place " +
            "INNER JOIN categoryEntity ON place.categoryId = categoryEntity.categoryId " +
            "INNER JOIN cityEntity ON place.cityId = cityEntity.cityId " +
            "WHERE placeName LIKE :keyword " +
            "OR categoryEntity.category LIKE :keyword " +
            "OR cityEntity.city LIKE :keyword " +
            "OR address LIKE :keyword LIMIT 4"
    )
    fun searchPlaceList(keyword: String): List<PlaceEntity>
}

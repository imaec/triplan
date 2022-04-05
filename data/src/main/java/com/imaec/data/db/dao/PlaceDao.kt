package com.imaec.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.imaec.data.entity.PlaceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDao : BaseDao<PlaceEntity> {

    @Query(
        value = "SELECT * FROM placeEntity AS place " +
            "INNER JOIN categoryEntity ON place.categoryId = categoryEntity.categoryId " +
            "INNER JOIN cityEntity ON place.cityId = cityEntity.cityId"
    )
    fun getPlaceList(): Flow<List<PlaceEntity>>
}

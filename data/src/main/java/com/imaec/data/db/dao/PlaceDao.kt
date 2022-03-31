package com.imaec.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.imaec.data.entity.PlaceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDao : BaseDao<PlaceEntity> {

    @Query("SELECT * FROM placeEntity")
    fun getPlaceList(): Flow<List<PlaceEntity>>
}

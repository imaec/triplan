package com.imaec.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.imaec.data.entity.PlaceEntity

@Dao
interface PlaceDao : BaseDao<PlaceEntity> {

    @Query("SELECT * FROM placeEntity")
    fun getPlaceList(): LiveData<List<PlaceEntity>>
}

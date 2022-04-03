package com.imaec.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.imaec.data.entity.CityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao : BaseDao<CityEntity> {

    @Query("SELECT * FROM cityEntity")
    fun getCityList(): Flow<List<CityEntity>>

    @Query("SELECT COUNT(*) FROM cityEntity WHERE :city = city")
    fun getCountByCity(city: String): Int
}

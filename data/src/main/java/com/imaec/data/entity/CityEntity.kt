package com.imaec.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cityEntity")
data class CityEntity(
    @PrimaryKey(autoGenerate = true) val cityId: Long = 0,
    @ColumnInfo val city: String
)

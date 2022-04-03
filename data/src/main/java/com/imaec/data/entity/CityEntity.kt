package com.imaec.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.imaec.domain.model.CityDto

@Entity(tableName = "cityEntity")
data class CityEntity(
    @PrimaryKey(autoGenerate = true) val cityId: Long = 0,
    @ColumnInfo val city: String,
    @ColumnInfo val defaultCity: Boolean = false
) {
    companion object {
        fun toDto(entity: CityEntity) = CityDto(
            cityId = entity.cityId,
            city = entity.city,
            defaultCity = entity.defaultCity
        )

        fun fromDto(dto: CityDto) = CityEntity(
            cityId = dto.cityId,
            city = dto.city,
            defaultCity = dto.defaultCity
        )
    }
}

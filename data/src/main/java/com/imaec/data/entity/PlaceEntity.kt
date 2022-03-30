package com.imaec.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.imaec.domain.model.PlaceDto

@Entity(tableName = "placeEntity")
data class PlaceEntity(
    @PrimaryKey(autoGenerate = true) val placeId: Long = 0,
    @ColumnInfo val placeName: String,
    @ColumnInfo val address: String = "",
    @ColumnInfo val siteUrl: String = "",
    @ColumnInfo val imageUrl: String = "",
    @ColumnInfo val saveTime: String
) {
    companion object {
        fun toDto(entity: PlaceEntity) = PlaceDto(
            placeId = entity.placeId,
            placeName = entity.placeName,
            address = entity.address,
            siteUrl = entity.siteUrl,
            imageUrl = entity.imageUrl,
            saveTime = entity.saveTime
        )
    }
}

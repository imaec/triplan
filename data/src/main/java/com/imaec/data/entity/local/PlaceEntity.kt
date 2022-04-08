package com.imaec.data.entity.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.imaec.domain.model.PlaceDto

@Entity(
    tableName = "placeEntity",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["categoryId"],
            childColumns = ["categoryId"],
            onUpdate = CASCADE
        ),
        ForeignKey(
            entity = CityEntity::class,
            parentColumns = ["cityId"],
            childColumns = ["cityId"],
            onUpdate = CASCADE
        ),
    ]
)
data class PlaceEntity(
    @PrimaryKey(autoGenerate = true) val placeId: Long = 0,
    @ColumnInfo val categoryId: Long,
    val category: String,
    @ColumnInfo val cityId: Long,
    val city: String,
    @ColumnInfo val placeName: String,
    @ColumnInfo val address: String = "",
    @ColumnInfo val siteUrl: String = "",
    @ColumnInfo val imageUrl: String = "",
    @ColumnInfo val saveTime: String
) {
    companion object {
        fun toDto(entity: PlaceEntity) = PlaceDto(
            placeId = entity.placeId,
            categoryId = entity.categoryId,
            category = entity.category,
            cityId = entity.cityId,
            city = entity.city,
            placeName = entity.placeName,
            address = entity.address,
            siteUrl = entity.siteUrl,
            imageUrl = entity.imageUrl,
            saveTime = entity.saveTime
        )
    }
}

package com.imaec.data.entity.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.imaec.domain.model.CategoryDto

@Entity(tableName = "categoryEntity")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val categoryId: Long = 0,
    @ColumnInfo val category: String
) {
    companion object {
        fun toDto(entity: CategoryEntity) = CategoryDto(
            categoryId = entity.categoryId,
            category = entity.category
        )

        fun fromDto(dto: CategoryDto) = CategoryEntity(
            categoryId = dto.categoryId,
            category = dto.category
        )
    }
}

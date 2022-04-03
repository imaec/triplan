package com.imaec.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categoryEntity")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val categoryId: Long = 0,
    @ColumnInfo val category: String
)

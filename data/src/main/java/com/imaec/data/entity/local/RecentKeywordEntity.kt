package com.imaec.data.entity.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.imaec.domain.model.RecentKeywordDto
import java.util.Date

@Entity(tableName = "recentKeywordEntity")
data class RecentKeywordEntity(
    @PrimaryKey
    val keyword: String,
    val updated: Date
) {
    companion object {
        fun toDto(entity: RecentKeywordEntity) = RecentKeywordDto(
            keyword = entity.keyword,
            updated = entity.updated
        )
    }
}

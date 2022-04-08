package com.imaec.data.entity.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.imaec.domain.model.PlanDto
import com.imaec.domain.model.PlanItemDto

@Entity(tableName = "planEntity")
data class PlanEntity(
    @PrimaryKey(autoGenerate = true) val planId: Long = 0,
    @ColumnInfo val planName: String,
    @ColumnInfo val planItemList: List<PlanItemDto>,
    @ColumnInfo val city: String,
    @ColumnInfo val startDate: String,
    @ColumnInfo val endDate: String
) {
    companion object {
        fun toDto(entity: PlanEntity) = PlanDto(
            planId = entity.planId,
            planName = entity.planName,
            planItemList = entity.planItemList,
            city = entity.city,
            startDate = entity.startDate,
            endDate = entity.endDate,
        )

        fun fromDto(dto: PlanDto) = PlanEntity(
            planId = dto.planId,
            planName = dto.planName,
            planItemList = dto.planItemList,
            city = dto.city,
            startDate = dto.startDate,
            endDate = dto.endDate,
        )
    }
}

package com.imaec.data.entity.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.imaec.domain.model.PlanDayDto
import com.imaec.domain.model.PlanDto

@Entity(tableName = "planEntity")
data class PlanEntity(
    @PrimaryKey(autoGenerate = true) val planId: Long = 0,
    @ColumnInfo val planName: String,
    @ColumnInfo val planDayList: List<PlanDayDto>,
    @ColumnInfo val city: String,
    @ColumnInfo val startDate: Long,
    @ColumnInfo val endDate: Long
) {
    companion object {
        fun toDto(entity: PlanEntity) = PlanDto(
            planId = entity.planId,
            planName = entity.planName,
            planDayList = entity.planDayList,
            city = entity.city,
            startDate = entity.startDate,
            endDate = entity.endDate,
        )

        fun fromDto(dto: PlanDto) = PlanEntity(
            planId = dto.planId,
            planName = dto.planName,
            planDayList = dto.planDayList,
            city = dto.city,
            startDate = dto.startDate,
            endDate = dto.endDate,
        )
    }
}

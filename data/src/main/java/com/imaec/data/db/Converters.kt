package com.imaec.data.db

import androidx.room.TypeConverter
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.imaec.domain.model.PlanDayDto
import com.imaec.domain.model.PlanItemDto
import java.time.LocalDate
import java.util.Date

class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun localDateToLong(date: LocalDate): Long =
        date.toEpochDay()

    @TypeConverter
    fun dataToLocalDate(data: Long): LocalDate = LocalDate.ofEpochDay(data)

    @TypeConverter
    fun planDayListToString(dto: List<PlanDayDto>): String =
        jacksonObjectMapper().writeValueAsString(dto) ?: ""

    @TypeConverter
    fun dataToPlanDayList(data: String): List<PlanDayDto> = jacksonObjectMapper().readValue(
        data,
        jacksonObjectMapper().typeFactory.constructCollectionType(
            List::class.java,
            PlanDayDto::class.java
        )
    )

    @TypeConverter
    fun planItemListToString(dto: List<PlanItemDto>): String =
        jacksonObjectMapper().writeValueAsString(dto) ?: ""

    @TypeConverter
    fun dataToPlanItemList(data: String): List<PlanItemDto> = jacksonObjectMapper().readValue(
        data,
        jacksonObjectMapper().typeFactory.constructCollectionType(
            List::class.java,
            PlanItemDto::class.java
        )
    )
}

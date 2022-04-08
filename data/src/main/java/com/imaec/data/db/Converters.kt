package com.imaec.data.db

import androidx.room.TypeConverter
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.imaec.domain.model.PlanItemDto
import java.time.LocalDate

class Converters {

    @TypeConverter
    fun localDateToLong(date: LocalDate): Long =
        date.toEpochDay()

    @TypeConverter
    fun dataToLocalDate(data: Long): LocalDate = LocalDate.ofEpochDay(data)

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

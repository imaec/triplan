package com.imaec.data.db

import androidx.room.TypeConverter
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.imaec.domain.model.PlanItemDto

class Converters {

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

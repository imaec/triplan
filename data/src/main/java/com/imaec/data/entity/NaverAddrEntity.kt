package com.imaec.data.entity

import com.imaec.domain.model.AddressDto
import com.imaec.domain.model.AddressElementDto
import com.imaec.domain.model.MetaDto
import com.imaec.domain.model.NaverAddrDto

data class NaverAddrEntity(
    val status: String,
    val meta: MetaEntity,
    val addresses: List<AddressEntity>,
    val errorMessage: String
) {
    fun toDto() = NaverAddrDto(
        status = status,
        meta = meta.toDto(),
        addresses = addresses.map { it.toDto() },
        errorMessage = errorMessage
    )
}

data class MetaEntity(
    val totalCount: Int,
    val page: Int,
    val count: Int
) {
    fun toDto() = MetaDto(
        totalCount = totalCount,
        page = page,
        count = count
    )
}

data class AddressEntity(
    val roadAddress: String,
    val jibunAddress: String,
    val englishAddress: String,
    val addressElements: List<AddressElementEntity>,
    val x: String,
    val y: String,
    val distance: Double
) {
    fun toDto() = AddressDto(
        roadAddress = roadAddress,
        jibunAddress = jibunAddress,
        englishAddress = englishAddress,
        addressElements = addressElements.map { it.toDto() },
        x = x,
        y = y,
        distance = distance
    )
}

data class AddressElementEntity(
    val types: List<String>,
    val longName: String,
    val shortName: String,
    val code: String
) {
    fun toDto() = AddressElementDto(
        types = types,
        longName = longName,
        shortName = shortName,
        code = code
    )
}

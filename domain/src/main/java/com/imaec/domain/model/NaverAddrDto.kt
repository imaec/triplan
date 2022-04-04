package com.imaec.domain.model

data class NaverAddrDto(
    val status: String,
    val meta: MetaDto,
    val addresses: List<AddressDto>,
    val errorMessage: String
)

data class MetaDto(
    val totalCount: Int,
    val page: Int,
    val count: Int
)

data class AddressDto(
    val roadAddress: String,
    val jibunAddress: String,
    val englishAddress: String,
    val addressElements: List<AddressElementDto>,
    val x: String,
    val y: String,
    val distance: Double
)

data class AddressElementDto(
    val types: List<String>,
    val longName: String,
    val shortName: String,
    val code: String
)

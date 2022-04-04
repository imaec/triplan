package com.imaec.domain.model

data class SearchAddressDto(
    val results: RoadAddressResultDto
)

data class RoadAddressResultDto(
    val common: RoadAddressCommonDto,
    val juso: List<RoadAddressDto>?
)

data class RoadAddressCommonDto(
    val totalCount: String,
    val currentPage: Int,
    val countPerPage: Int,
    val errorCode: String,
    val errorMessage: String
)

data class RoadAddressDto(
    val roadAddr: String? = "",
    val roadAddrPart1: String? = "",
    val roadAddrPart2: String? = "",
    val jibunAddr: String? = "",
    val engAddr: String? = "",
    val zipNo: String? = "",
    val admCd: String? = "",
    val rnMgtSn: String? = "",
    val bdMgtSn: String? = "",
    val detBdNmList: String? = "",
    val bdNm: String? = "",
    val bdKdcd: String? = "",
    val siNm: String? = "",
    val sggNm: String? = "",
    val emdNm: String? = "",
    val liNm: String? = "",
    val rn: String? = "",
    val udrtYn: String? = "",
    val buldMnnm: String? = "",
    val buldSlno: String? = "",
    val emdNo: String? = "",
    val entX: String? = "",
    val entY: String? = ""
)

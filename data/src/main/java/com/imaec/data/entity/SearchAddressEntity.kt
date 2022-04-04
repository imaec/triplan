package com.imaec.data.entity

import com.imaec.domain.model.RoadAddressCommonDto
import com.imaec.domain.model.RoadAddressDto
import com.imaec.domain.model.RoadAddressResultDto
import com.imaec.domain.model.SearchAddressDto

data class SearchAddressEntity(
    val results: RoadAddressResultEntity
) {
    fun toDto() = SearchAddressDto(
        results = results.toDto(),
    )
}

data class RoadAddressResultEntity(
    val common: RoadAddressCommonEntity,
    val juso: List<RoadAddressEntity?>?
) {
    fun toDto() = RoadAddressResultDto(
        common = common.toDto(),
        juso = juso?.map(RoadAddressEntity::toDto),
    )
}

data class RoadAddressCommonEntity(
    val totalCount: String,
    val currentPage: Int,
    val countPerPage: Int,
    val errorCode: String,
    val errorMessage: String
) {
    fun toDto() = RoadAddressCommonDto(
        totalCount = totalCount,
        currentPage = currentPage,
        countPerPage = countPerPage,
        errorCode = errorCode,
        errorMessage = errorMessage
    )
}

data class RoadAddressEntity(
    val roadAddr: String?,
    val roadAddrPart1: String?,
    val roadAddrPart2: String?,
    val jibunAddr: String?,
    val engAddr: String?,
    val zipNo: String?,
    val admCd: String?,
    val rnMgtSn: String?,
    val bdMgtSn: String?,
    val detBdNmList: String?,
    val bdNm: String?,
    val bdKdcd: String?,
    val siNm: String?,
    val sggNm: String?,
    val emdNm: String?,
    val liNm: String?,
    val rn: String?,
    val udrtYn: String?,
    val buldMnnm: String?,
    val buldSlno: String?,
    val emdNo: String?,
    val entX: String?,
    val entY: String?,
) {
    companion object {
        fun toDto(entity: RoadAddressEntity?) = RoadAddressDto(
            roadAddr = entity?.roadAddr,
            roadAddrPart1 = entity?.roadAddrPart1,
            roadAddrPart2 = entity?.roadAddrPart2,
            jibunAddr = entity?.jibunAddr,
            engAddr = entity?.engAddr,
            zipNo = entity?.zipNo,
            admCd = entity?.admCd,
            rnMgtSn = entity?.rnMgtSn,
            bdMgtSn = entity?.bdMgtSn,
            detBdNmList = entity?.detBdNmList,
            bdNm = entity?.bdNm,
            bdKdcd = entity?.bdKdcd,
            siNm = entity?.siNm,
            sggNm = entity?.sggNm,
            emdNm = entity?.emdNm,
            liNm = entity?.liNm,
            rn = entity?.rn,
            udrtYn = entity?.udrtYn,
            buldMnnm = entity?.buldMnnm,
            buldSlno = entity?.buldSlno,
            emdNo = entity?.emdNo,
            entX = entity?.entX,
            entY = entity?.entY
        )
    }
}

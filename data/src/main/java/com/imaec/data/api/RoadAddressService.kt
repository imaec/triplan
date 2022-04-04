package com.imaec.data.api

import com.imaec.data.entity.SearchAddressEntity
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface RoadAddressService {

    @GET("addrlink/addrLinkApi.do")
    suspend fun getAddress(@QueryMap map: Map<String, String>): SearchAddressEntity
}

package com.imaec.data.api

import com.imaec.data.entity.NaverAddrEntity
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface NaverService {

    @GET("map-geocode/v2/geocode")
    suspend fun getAddress(@QueryMap(encoded = false) map: Map<String, String>): NaverAddrEntity
}

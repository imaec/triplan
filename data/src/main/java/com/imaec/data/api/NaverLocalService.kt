package com.imaec.data.api

import com.imaec.data.entity.NaverPlaceResultEntity
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface NaverLocalService {

    @GET("v1/search/local.json")
    suspend fun getPlace(@QueryMap map: Map<String, String>): NaverPlaceResultEntity
}

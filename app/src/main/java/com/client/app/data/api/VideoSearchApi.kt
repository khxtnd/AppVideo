package com.client.app.data.api


import com.client.app.domain.entities.DataListApi
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface VideoSearchApi {

    @GET("video-service/v1/video/search/v2")
    fun getVideoSearch(
        @Query("msisdn") msisdn: String,
        @Query("timestamp") timestamp: String,
        @Query("security") security: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("q") q: String,
        @Query("lastHashId") lastHashId: String
    ): Call<DataListApi>
}
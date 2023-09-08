package com.client.app.data.api


import com.client.app.domain.entities.DataListApi
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface VideoListApi {

    @GET("video-service/v1/video/hot")
    fun getVideos(
        @Query("msisdn") msisdn: String,
        @Query("timestamp") timestamp: String,
        @Query("security") security: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("lastHashId") lastHashId: String
    ): Call<DataListApi>
}
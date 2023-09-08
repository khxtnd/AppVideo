package com.client.app.data.api


import com.client.app.domain.entities.DataDetailApi
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface VideoDetailApi {

    @GET("video-service/v1/video/{id}/info")
    fun getVideoDetail(
        @Path("id") id: Int,
        @Query("msisdn") msisdn: String,
        @Query("timestamp") timestamp: String,
        @Query("security") security: String,
    ): Call<DataDetailApi>
}
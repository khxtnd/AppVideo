package com.client.app.data


import com.client.app.entities.DataDetailApi
import com.client.app.entities.DataListApi
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("video-service/v1/video/{id}/info")
    fun getVideoDetail(
        @Path("id") id: Int,
        @Query("msisdn") msisdn: String,
        @Query("timestamp") timestamp: String,
        @Query("security") security: String,
    ): Call<DataDetailApi>

    @GET("video-service/v1/video/hot")
    fun getVideoHot(
        @Query("msisdn") msisdn: String,
        @Query("timestamp") timestamp: String,
        @Query("security") security: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("lastHashId") lastHashId: String
    ): Call<DataListApi>

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
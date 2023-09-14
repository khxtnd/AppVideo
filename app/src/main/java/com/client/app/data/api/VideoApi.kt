package com.client.app.data.api


import com.client.app.data.api.entities.AppResponse
import com.client.app.domain.entities.Video
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface VideoApi {

    @GET("video-service/v1/video/{id}/info")
    suspend fun getVideoDetail(
        @Path("id") id: Int,
        @Query("msisdn") msisdn: String,
        @Query("timestamp") timestamp: String,
        @Query("security") security: String,
    ): AppResponse<Video>

    @GET("video-service/v1/video/hot")
    suspend fun getVideoHot(
        @Query("msisdn") msisdn: String,
        @Query("timestamp") timestamp: String,
        @Query("security") security: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("lastHashId") lastHashId: String
    ): AppResponse<List<Video>>

    @GET("video-service/v1/video/search/v2")
    suspend fun searchListVideo(
        @Query("msisdn") msisdn: String,
        @Query("timestamp") timestamp: String,
        @Query("security") security: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("q") keySearch: String,
        @Query("lastHashId") lastHashId: String
    ): AppResponse<List<Video>>
}
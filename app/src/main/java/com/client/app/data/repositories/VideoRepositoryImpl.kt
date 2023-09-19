package com.client.app.data.repositories

import android.util.Log
import com.client.app.data.api.VideoApi
import com.client.app.domain.entities.Video
import com.client.app.domain.repositories.VideoRepository

class VideoRepositoryImpl(private val videoApi: VideoApi):VideoRepository {
    override suspend fun getVideoDetail(
        id: Int,
        msisdn: String,
        timestamp: String,
        security: String
    ): Video {
        return videoApi.getVideoDetail(id, msisdn, timestamp, security).data
    }

    override suspend fun getVideoHot(
        msisdn: String,
        timestamp: String,
        security: String,
        page: Int,
        size: Int,
        lastHashId: String
    ): List<Video> {
        return  videoApi.getVideoHot(msisdn, timestamp, security, page, size, lastHashId).data
    }

    override suspend fun searchListVideo(
        msisdn: String,
        timestamp: String,
        security: String,
        page: Int,
        size: Int,
        keySearch: String,
        lastHashId: String
    ): List<Video> {
        return videoApi.searchListVideo(msisdn, timestamp, security, page, size, keySearch, lastHashId).data
    }
}
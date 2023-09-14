package com.client.app.domain.repositories

import com.client.app.domain.entities.Video

interface VideoRepository {
    suspend fun getVideoDetail(
        id: Int,
        msisdn: String,
        timestamp: String,
        security: String,
    ): Video

    suspend fun getVideoHot(
        msisdn: String,
        timestamp: String,
        security: String,
        page: Int,
        size: Int,
        lastHashId: String
    ): List<Video>

    suspend fun searchListVideo(
        msisdn: String,
        timestamp: String,
        security: String,
        page: Int,
        size: Int,
        keySearch: String,
        lastHashId: String
    ): List<Video>
}
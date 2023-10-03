package com.client.app.domain.repositories

import com.client.app.data.database.entities.WatchedVideo
import kotlinx.coroutines.flow.Flow

interface WatchedVideoRepository {

    suspend fun insertWatchVideo(watchedVideo: WatchedVideo)

    suspend fun deleteWatchVideo(watchedVideo: WatchedVideo)

    fun getAllWatchVideo(): Flow<List<WatchedVideo>>

    fun checkWatchHistoryExisted(id: Int): Int
}
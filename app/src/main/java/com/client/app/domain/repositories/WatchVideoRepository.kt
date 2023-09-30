package com.client.app.domain.repositories

import androidx.lifecycle.LiveData
import com.client.app.data.database.entities.WatchVideo

interface WatchVideoRepository {

    suspend fun insertWatchVideo(watchVideo: WatchVideo)

    suspend fun deleteWatchVideo(watchVideo: WatchVideo)

    fun getAllWatchVideo(): LiveData<List<WatchVideo>>

    fun checkWatchHistoryExisted(id: Int): Int
}
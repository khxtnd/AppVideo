package com.client.app.domain.usecases

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import com.client.app.data.database.entities.WatchVideo
import com.client.app.domain.repositories.WatchVideoRepository

class GetWatchVideoUseCase(private val watchVideoRepository: WatchVideoRepository) {

    suspend fun insertWatchVideo(watchVideo: WatchVideo) {
        return watchVideoRepository.insertWatchVideo(watchVideo)
    }

    suspend fun deleteWatchVideo(watchVideo: WatchVideo) {
        return watchVideoRepository.deleteWatchVideo(watchVideo)
    }

    fun getAllWatchVideo(): LiveData<List<WatchVideo>> {
        return watchVideoRepository.getAllWatchVideo()
    }

    fun checkWatchVideoExisted(id: Int): Int {
        return watchVideoRepository.checkWatchHistoryExisted(id)
    }
}
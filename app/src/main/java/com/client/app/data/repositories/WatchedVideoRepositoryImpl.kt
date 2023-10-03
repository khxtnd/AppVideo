package com.client.app.data.repositories

import android.app.Application
import com.client.app.data.database.HistoryDatabase
import com.client.app.data.database.WatchedVideoDao
import com.client.app.data.database.entities.WatchedVideo
import com.client.app.domain.repositories.WatchedVideoRepository
import kotlinx.coroutines.flow.Flow

class WatchedVideoRepositoryImpl(application: Application): WatchedVideoRepository {
    private val watchedVideoDao: WatchedVideoDao

    init {
        val historyDatabase: HistoryDatabase = HistoryDatabase.getInstance(application)
        watchedVideoDao = historyDatabase.getWatchHistoryDao()
    }

    override suspend fun insertWatchVideo(watchedVideo: WatchedVideo) {
        return watchedVideoDao.insertWatchVideo(watchedVideo)
    }

    override suspend fun deleteWatchVideo(watchedVideo: WatchedVideo) {
        return watchedVideoDao.deleteWatchVideo(watchedVideo)
    }

    override fun getAllWatchVideo(): Flow<List<WatchedVideo>> {
        return watchedVideoDao.getAllWatchVideo()
    }

    override fun checkWatchHistoryExisted(id: Int): Int {
        return watchedVideoDao.checkWatchVideoExisted(id)
    }


}
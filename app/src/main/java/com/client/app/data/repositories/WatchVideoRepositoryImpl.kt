package com.client.app.data.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.client.app.data.database.HistoryDatabase
import com.client.app.data.database.WatchVideoDao
import com.client.app.data.database.entities.WatchVideo
import com.client.app.domain.repositories.WatchVideoRepository

class WatchVideoRepositoryImpl(application: Application): WatchVideoRepository {
    private val watchVideoDao: WatchVideoDao

    init {
        val historyDatabase: HistoryDatabase = HistoryDatabase.getInstance(application)
        watchVideoDao = historyDatabase.getWatchHistoryDao()
    }

    override suspend fun insertWatchVideo(watchVideo: WatchVideo) {
        return watchVideoDao.insertWatchVideo(watchVideo)
    }

    override suspend fun deleteWatchVideo(watchVideo: WatchVideo) {
        return watchVideoDao.deleteWatchVideo(watchVideo)
    }

    override fun getAllWatchVideo(): LiveData<List<WatchVideo>> {
        return watchVideoDao.getAllWatchVideo()
    }

    override fun checkWatchHistoryExisted(id: Int): Int {
        return watchVideoDao.checkWatchVideoExisted(id)
    }


}
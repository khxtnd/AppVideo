package com.client.app.di

import android.app.Application
import com.client.app.DataBaseApplication
import com.client.app.data.repositories.SearchHistoryRepositoryImpl
import com.client.app.data.repositories.VideoRepositoryImpl
import com.client.app.data.repositories.WatchVideoRepositoryImpl
import com.client.app.domain.repositories.SearchHistoryRepository
import com.client.app.domain.repositories.VideoRepository
import com.client.app.domain.repositories.WatchVideoRepository

val videoRepository:VideoRepository by lazy {
    VideoRepositoryImpl(videoApi)
}
val searchHistoryRepository:SearchHistoryRepository by lazy {
    SearchHistoryRepositoryImpl(DataBaseApplication.share)
}
val watchVideoRepository:WatchVideoRepository by lazy {
    WatchVideoRepositoryImpl(DataBaseApplication.share)
}
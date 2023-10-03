package com.client.app.di

import com.client.app.DataBaseApplication
import com.client.app.data.repositories.SearchHistoryRepositoryImpl
import com.client.app.data.repositories.VideoRepositoryImpl
import com.client.app.data.repositories.WatchedVideoRepositoryImpl
import com.client.app.domain.repositories.SearchHistoryRepository
import com.client.app.domain.repositories.VideoRepository
import com.client.app.domain.repositories.WatchedVideoRepository

val videoRepository:VideoRepository by lazy {
    VideoRepositoryImpl(videoApi)
}
val searchHistoryRepository:SearchHistoryRepository by lazy {
    SearchHistoryRepositoryImpl(DataBaseApplication.share)
}
val watchedVideoRepository:WatchedVideoRepository by lazy {
    WatchedVideoRepositoryImpl(DataBaseApplication.share)
}
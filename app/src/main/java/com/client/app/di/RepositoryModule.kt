package com.client.app.di

import com.client.app.data.repositories.VideoRepositoryImpl
import com.client.app.domain.repositories.VideoRepository

val videoRepository:VideoRepository by lazy {
    VideoRepositoryImpl(videoApi)
}

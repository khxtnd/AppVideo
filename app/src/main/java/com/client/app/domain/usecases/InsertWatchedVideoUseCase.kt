package com.client.app.domain.usecases

import com.client.app.data.database.entities.SearchHistory
import com.client.app.data.database.entities.WatchedVideo
import com.client.app.domain.repositories.WatchedVideoRepository
import kotlinx.coroutines.flow.Flow

class InsertWatchedVideoUseCase(private val watchedVideoRepository: WatchedVideoRepository)
    :BaseUseCase<InsertWatchedVideoUseCase.Param,Unit>{
    data class Param(
        val watchedVideo: WatchedVideo
    )

    override suspend fun invoke(param: Param) {
        watchedVideoRepository.insertWatchVideo(param.watchedVideo)
    }

}
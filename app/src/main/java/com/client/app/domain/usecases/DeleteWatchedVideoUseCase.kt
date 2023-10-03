package com.client.app.domain.usecases

import com.client.app.data.database.entities.SearchHistory
import com.client.app.data.database.entities.WatchedVideo
import com.client.app.domain.repositories.SearchHistoryRepository
import com.client.app.domain.repositories.WatchedVideoRepository

class DeleteWatchedVideoUseCase(private val watchedVideoRepository: WatchedVideoRepository):
    BaseUseCase<DeleteWatchedVideoUseCase.Param,Unit> {
    data class Param(
        val watchedVideo: WatchedVideo
    )

    override suspend fun invoke(param: Param) {
        watchedVideoRepository.deleteWatchVideo(param.watchedVideo)
    }


}
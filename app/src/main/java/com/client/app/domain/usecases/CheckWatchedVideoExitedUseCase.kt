package com.client.app.domain.usecases

import com.client.app.data.database.entities.SearchHistory
import com.client.app.domain.repositories.SearchHistoryRepository
import com.client.app.domain.repositories.WatchedVideoRepository

class CheckWatchedVideoExitedUseCase(private val watchedVideoRepository: WatchedVideoRepository):
    BaseUseCase<CheckWatchedVideoExitedUseCase.Param,Int>{
    data class Param(
        val id:Int
    )

    override suspend fun invoke(param: Param): Int {
        return watchedVideoRepository.checkWatchHistoryExisted(param.id)
    }

}
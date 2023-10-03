package com.client.app.domain.usecases

import android.util.Log
import com.client.app.data.database.entities.SearchHistory
import com.client.app.domain.repositories.SearchHistoryRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.client.app.data.database.entities.WatchedVideo
import com.client.app.domain.repositories.WatchedVideoRepository
import kotlinx.coroutines.flow.Flow

class GetAllWatchedVideoUseCase(private val watchedVideoRepository: WatchedVideoRepository):
    BaseUseCase<Unit, Flow<List<WatchedVideo>>> {
    override suspend fun invoke(param: Unit): Flow<List<WatchedVideo>> {
        return watchedVideoRepository.getAllWatchVideo()
    }

}
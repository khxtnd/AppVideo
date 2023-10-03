package com.client.app.domain.usecases

import android.util.Log
import com.client.app.data.database.entities.SearchHistory
import com.client.app.domain.repositories.SearchHistoryRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.Flow

class GetAllSearchHistoryUseCase(private val searchHistoryRepository: SearchHistoryRepository):
    BaseUseCase<Unit, Flow<List<SearchHistory>>> {
    override suspend fun invoke(param: Unit): Flow<List<SearchHistory>> {
        return searchHistoryRepository.getAllSearchHistory()
    }

}
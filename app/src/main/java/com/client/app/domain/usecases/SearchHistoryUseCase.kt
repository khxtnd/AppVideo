package com.client.app.domain.usecases

import androidx.lifecycle.LiveData
import com.client.app.data.database.entities.SearchHistory
import com.client.app.domain.repositories.SearchHistoryRepository

class SearchHistoryUseCase(private val searchHistoryRepository: SearchHistoryRepository) {
    suspend fun insertSearchHistory(searchHistory: SearchHistory) {
        return searchHistoryRepository.insertSearchHistory(searchHistory)
    }

    suspend fun deleteSearchHistory(searchHistory: SearchHistory) {
        return searchHistoryRepository.deleteSearchHistory(searchHistory)
    }

    fun getAllSearchHistory(): LiveData<List<SearchHistory>> {
        return searchHistoryRepository.getAllSearchHistory()
    }

    suspend fun deleteAllSearchHistory() {
        return searchHistoryRepository.deleteAllSearchHistory()
    }

    fun checkSearchHistoryExisted(keySearch: String): Int {
        return searchHistoryRepository.checkSearchHistoryExisted(keySearch)
    }
}
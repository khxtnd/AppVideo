package com.client.app.domain.usecases

import com.client.app.data.database.entities.SearchHistory
import com.client.app.domain.repositories.SearchHistoryRepository

class InsertSearchHistoryUseCase(private val searchHistoryRepository: SearchHistoryRepository):
    BaseUseCase<InsertSearchHistoryUseCase.Param,Unit> {
    data class Param(
        val searchHistory: SearchHistory
    )

    override suspend fun invoke(param: Param) {
        searchHistoryRepository.insertSearchHistory(param.searchHistory)
    }

}
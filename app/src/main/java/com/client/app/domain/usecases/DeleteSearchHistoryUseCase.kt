package com.client.app.domain.usecases

import com.client.app.data.database.entities.SearchHistory
import com.client.app.domain.repositories.SearchHistoryRepository

class DeleteSearchHistoryUseCase(private val searchHistoryRepository: SearchHistoryRepository):
    BaseUseCase<DeleteSearchHistoryUseCase.Param,Unit> {
    data class Param(
        val searchHistory: SearchHistory
    )

    override suspend fun invoke(param: Param) {
        searchHistoryRepository.deleteSearchHistory(param.searchHistory)
    }

}
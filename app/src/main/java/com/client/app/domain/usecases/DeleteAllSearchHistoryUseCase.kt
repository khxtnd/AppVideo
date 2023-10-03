package com.client.app.domain.usecases

import com.client.app.domain.repositories.SearchHistoryRepository

class DeleteAllSearchHistoryUseCase(private val searchHistoryRepository: SearchHistoryRepository) :
    BaseUseCase<Unit, Unit> {
    override suspend fun invoke(param: Unit) {
        searchHistoryRepository.deleteAllSearchHistory()
    }

}
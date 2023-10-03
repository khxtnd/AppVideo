package com.client.app.domain.usecases

import com.client.app.data.database.entities.SearchHistory
import com.client.app.domain.repositories.SearchHistoryRepository

class CheckSearchHistoryExitedUseCase(private val searchHistoryRepository: SearchHistoryRepository):
    BaseUseCase<CheckSearchHistoryExitedUseCase.Param,Int>{
    data class Param(
        val keySearch:String
    )

    override suspend fun invoke(param: Param): Int {
        return searchHistoryRepository.checkSearchHistoryExisted(param.keySearch)
    }

}
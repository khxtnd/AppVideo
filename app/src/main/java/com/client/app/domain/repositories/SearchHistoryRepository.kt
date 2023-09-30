package com.client.app.domain.repositories

import androidx.lifecycle.LiveData
import com.client.app.data.database.entities.SearchHistory

interface SearchHistoryRepository {
    suspend fun insertSearchHistory(searchHistory: SearchHistory)

    suspend fun deleteSearchHistory(searchHistory: SearchHistory)

    fun getAllSearchHistory(): LiveData<List<SearchHistory>>

    suspend fun deleteAllSearchHistory()

    fun checkSearchHistoryExisted(keySearch: String): Int
}
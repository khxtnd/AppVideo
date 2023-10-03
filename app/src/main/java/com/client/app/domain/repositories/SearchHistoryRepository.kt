package com.client.app.domain.repositories

import androidx.lifecycle.LiveData
import com.client.app.data.database.entities.SearchHistory
import kotlinx.coroutines.flow.Flow

interface SearchHistoryRepository {
    suspend fun insertSearchHistory(searchHistory: SearchHistory)

    suspend fun deleteSearchHistory(searchHistory: SearchHistory)

    fun getAllSearchHistory(): Flow<List<SearchHistory>>

    suspend fun deleteAllSearchHistory()

    fun checkSearchHistoryExisted(keySearch: String): Int
}
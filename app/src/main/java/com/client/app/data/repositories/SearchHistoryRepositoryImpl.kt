package com.client.app.data.repositories

import android.app.Application
import android.content.Context
import android.util.Log
import com.client.app.data.database.HistoryDatabase
import com.client.app.data.database.SearchHistoryDao
import com.client.app.data.database.entities.SearchHistory
import com.client.app.domain.repositories.SearchHistoryRepository
import kotlinx.coroutines.flow.Flow
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData

class SearchHistoryRepositoryImpl(context: Context) : SearchHistoryRepository {
    private val searchHistoryDao: SearchHistoryDao

    init {
        val historyDatabase: HistoryDatabase = HistoryDatabase.getInstance(context)
        searchHistoryDao = historyDatabase.getSearchHistoryDao()
    }

    override suspend fun insertSearchHistory(searchHistory: SearchHistory) {
        return searchHistoryDao.insertSearchHistory(searchHistory)
    }

    override suspend fun deleteSearchHistory(searchHistory: SearchHistory) {
        return searchHistoryDao.deleteSearchHistory(searchHistory)
    }

    override fun getAllSearchHistory(): Flow<List<SearchHistory>>{
        Log.i("RepositoryImpl",searchHistoryDao.getAllSearchHistory().asLiveData().value?.size.toString())
        return searchHistoryDao.getAllSearchHistory()
    }

    override suspend fun deleteAllSearchHistory() {
        return searchHistoryDao.deleteAllSearchHistory()
    }

    override fun checkSearchHistoryExisted(keySearch: String): Int {
        return searchHistoryDao.checkSearchHistoryExisted(keySearch)
    }


}
package com.client.app.data.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.client.app.data.database.HistoryDatabase
import com.client.app.data.database.SearchHistoryDao
import com.client.app.data.database.entities.SearchHistory
import com.client.app.domain.repositories.SearchHistoryRepository

class SearchHistoryRepositoryImpl(application: Application) : SearchHistoryRepository {
    private val searchHistoryDao: SearchHistoryDao

    init {
        val historyDatabase: HistoryDatabase = HistoryDatabase.getInstance(application)
        searchHistoryDao = historyDatabase.getSearchHistoryDao()
    }

    override suspend fun insertSearchHistory(searchHistory: SearchHistory) {
        return searchHistoryDao.insertSearchHistory(searchHistory)
    }

    override suspend fun deleteSearchHistory(searchHistory: SearchHistory) {
        return searchHistoryDao.deleteSearchHistory(searchHistory)
    }

    override fun getAllSearchHistory(): LiveData<List<SearchHistory>> {
        return searchHistoryDao.getAllSearchHistory()
    }

    override suspend fun deleteAllSearchHistory() {
        return searchHistoryDao.deleteAllSearchHistory()
    }

    override fun checkSearchHistoryExisted(keySearch: String): Int {
        return searchHistoryDao.checkSearchHistoryExisted(keySearch)
    }


}
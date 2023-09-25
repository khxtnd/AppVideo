package com.client.app.ui.search

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.client.app.data.database.entities.SearchHistory
import com.client.app.domain.entities.Video
import com.client.app.domain.repositories.SearchHistoryRepository
import com.client.app.domain.usecases.SearchListVideoUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel(
    private val searchListVideoUseCase: SearchListVideoUseCase,
    application: Application
) : ViewModel() {
    private val handler = CoroutineExceptionHandler { _, _ ->

    }

    val listVideo: LiveData<List<Video>> = MediatorLiveData()
    fun search(keySearch: String) = viewModelScope.launch(handler + Dispatchers.IO) {
        val msisdn = "0969633777"
        val timestamp = "123"
        val security = "123"
        val page = 0
        val size = 10
        val lastHashId = "13asd"

        (listVideo as MediatorLiveData).postValue(
            searchListVideoUseCase.invoke(
                SearchListVideoUseCase.Param(
                    msisdn,
                    timestamp,
                    security,
                    page,
                    size,
                    keySearch,
                    lastHashId
                )
            )
        )
    }

    private val searchHistoryRepository: SearchHistoryRepository =
        SearchHistoryRepository(application)

    fun insertSearchHistory(searchHistory: SearchHistory) = viewModelScope.launch {
        val count = withContext(Dispatchers.IO) {
            searchHistoryRepository.checkSearchHistoryExisted(searchHistory.keySearch)
        }

        if (count == 0) {
            withContext(Dispatchers.IO) {
                searchHistoryRepository.insertSearchHistory(searchHistory)
            }
        }
    }

    fun deleteSearchHistory(searchHistory: SearchHistory) = viewModelScope.launch {
        searchHistoryRepository.deleteSearchHistory(searchHistory)
    }

    fun getAllSearchHistory(): LiveData<List<SearchHistory>> =
        searchHistoryRepository.getAllSearchHistory()

    suspend fun deleteAllSearchHistory() = viewModelScope.launch {
        searchHistoryRepository.deleteAllSearchHistory()
    }
}

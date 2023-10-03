package com.client.app.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.client.app.data.database.entities.SearchHistory
import com.client.app.domain.entities.Video
import com.client.app.domain.usecases.CheckSearchHistoryExitedUseCase
import com.client.app.domain.usecases.DeleteAllSearchHistoryUseCase
import com.client.app.domain.usecases.DeleteSearchHistoryUseCase
import com.client.app.domain.usecases.GetAllSearchHistoryUseCase
import com.client.app.domain.usecases.InsertSearchHistoryUseCase
import com.client.app.domain.usecases.SearchListVideoUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel(
    private val searchListVideoUseCase: SearchListVideoUseCase,
    private val deleteAllSearchHistoryUseCase: DeleteAllSearchHistoryUseCase,
    private val deleteSearchHistoryUseCase: DeleteSearchHistoryUseCase,
    private val checkSearchHistoryExitedUseCase: CheckSearchHistoryExitedUseCase,
    private val getAllSearchHistoryUseCase: GetAllSearchHistoryUseCase,
    private val insertSearchHistoryUseCase: InsertSearchHistoryUseCase
) : ViewModel() {
    private val handler = CoroutineExceptionHandler { _, _ ->

    }

    val listVideo: LiveData<List<Video>> = MediatorLiveData()
    val listSearchHistory: LiveData<List<SearchHistory>> = MediatorLiveData()

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


    fun insertSearchHistory(searchHistory: SearchHistory) = viewModelScope.launch {
        val count = withContext(Dispatchers.IO) {
            checkSearchHistoryExitedUseCase.invoke(
                CheckSearchHistoryExitedUseCase.Param(searchHistory.keySearch)
            )
        }

        if (count == 0) {
            withContext(Dispatchers.IO) {
                insertSearchHistoryUseCase.invoke(
                    InsertSearchHistoryUseCase.Param(searchHistory)
                )
            }
        }
    }

    fun deleteSearchHistory(searchHistory: SearchHistory) = viewModelScope.launch {
        deleteSearchHistoryUseCase.invoke(
            DeleteSearchHistoryUseCase.Param(searchHistory)
        )
    }

    fun getAllSearchHistory() = viewModelScope.launch {
        val searchHistoryFlow = getAllSearchHistoryUseCase.invoke(Unit)
        searchHistoryFlow.collect { searchHistoryList ->
            (listSearchHistory as MediatorLiveData).postValue(searchHistoryList)
        }
    }

    suspend fun deleteAllSearchHistory() = viewModelScope.launch {
        deleteAllSearchHistoryUseCase.invoke(Unit)
    }
}



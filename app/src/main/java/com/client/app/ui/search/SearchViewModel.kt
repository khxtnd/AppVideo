package com.client.app.ui.search

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.client.app.data.database.entities.History
import com.client.app.domain.entities.Video
import com.client.app.domain.repositories.HistoryRepository
import com.client.app.domain.usecases.SearchListVideoUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

    private val historyRepository: HistoryRepository = HistoryRepository(application)

    fun insertHistory(history: History) = viewModelScope.launch {
        historyRepository.insertHistory(history)
    }

    fun deleteHistory(history: History) = viewModelScope.launch {
        historyRepository.deleteHistory(history)
    }

    fun getAllHistory(): LiveData<List<History>> = historyRepository.getAllHistory()

    suspend fun deleteAllHistory() = viewModelScope.launch {
        historyRepository.deleteAllHistory()
    }
}

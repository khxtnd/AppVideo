package com.client.app.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.client.app.data.database.entities.SearchHistory
import com.client.app.data.database.entities.WatchedVideo
import com.client.app.domain.entities.Video
import com.client.app.domain.usecases.DeleteSearchHistoryUseCase
import com.client.app.domain.usecases.DeleteWatchedVideoUseCase
import com.client.app.domain.usecases.GetAllWatchedVideoUseCase
import com.client.app.domain.usecases.GetListVideoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeViewModel(
    private val getListVideoUseCase: GetListVideoUseCase,
    private val  getAllWatchedVideoUseCase: GetAllWatchedVideoUseCase,
    private val deleteWatchedVideoUseCase: DeleteWatchedVideoUseCase
) : ViewModel() {
    val listWatchedVideo: LiveData<List<WatchedVideo>> = MediatorLiveData()

    val videoHot: LiveData<List<Video>> = MutableLiveData<List<Video>>().apply {
        viewModelScope.launch(Dispatchers.IO) {
            val msisdn = "0969633777"
            val timestamp = "123"
            val security = "123"
            val page = 0
            val size = 10
            val lastHashId = "13asd"

            val listVideo = getListVideoUseCase.invoke(
                GetListVideoUseCase.Param(
                    msisdn,
                    timestamp,
                    security,
                    page,
                    size,
                    lastHashId
                )
            )
            postValue(listVideo)
        }

    }

    fun deleteWatchHistory(watchedVideo: WatchedVideo) = viewModelScope.launch {
        deleteWatchedVideoUseCase.invoke(
            DeleteWatchedVideoUseCase.Param(watchedVideo)
        )
    }

    fun getAllWatchHistory() = viewModelScope.launch {
        getAllWatchedVideoUseCase.invoke(Unit).collect { watchedVideoList ->
            (listWatchedVideo as MediatorLiveData).postValue(watchedVideoList)
        }
    }

}

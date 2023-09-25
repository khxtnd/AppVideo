package com.client.app.ui.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.client.app.data.database.entities.WatchVideo
import com.client.app.domain.entities.Video
import com.client.app.domain.repositories.WatchVideoRepository
import com.client.app.domain.usecases.GetListVideoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeViewModel(private val getListVideoUseCase: GetListVideoUseCase, application: Application) : ViewModel() {
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
    private val watchVideoRepository: WatchVideoRepository =
        WatchVideoRepository(application)

    fun deleteWatchHistory(watchVideo: WatchVideo) = viewModelScope.launch {
        watchVideoRepository.deleteWatchVideo(watchVideo)
    }

    fun getAllWatchHistory(): LiveData<List<WatchVideo>> =
        watchVideoRepository.getAllWatchVideo()

}

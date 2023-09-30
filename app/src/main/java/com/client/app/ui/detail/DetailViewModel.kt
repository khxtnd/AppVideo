package com.client.app.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.client.app.data.database.entities.WatchVideo
import com.client.app.domain.entities.Video
import com.client.app.domain.usecases.GetVideoInfoUseCase
import com.client.app.domain.usecases.GetWatchVideoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DetailViewModel(
    private val getVideoInfoUseCase: GetVideoInfoUseCase,
    private val getWatchVideoUseCase: GetWatchVideoUseCase
) : ViewModel() {
    private var currentPosition: Long = 0
    private var videoId: LiveData<Int> = MediatorLiveData()
    val videoInfo: LiveData<Video> = MediatorLiveData<Video>().apply {
        addSource(videoId) {
            val msisdn = "0969633777"
            val timestamp = "123"
            val security = "123"
            val videoId = videoId.value ?: return@addSource
            viewModelScope.launch(Dispatchers.IO) {
                postValue(
                    getVideoInfoUseCase.invoke(
                        GetVideoInfoUseCase.Param(
                            videoId,
                            msisdn,
                            timestamp,
                            security
                        )
                    )
                )
            }
        }
    }

    fun setId(id: Int) {
        (videoId as MediatorLiveData).postValue(id)
    }
    fun setCurrentPosition(currentPosition: Long) {
        this.currentPosition=currentPosition
    }
    fun getCurrentPosition() :Long{
        return currentPosition
    }
    fun insertWatchVideo(watchVideo: WatchVideo) = viewModelScope.launch {
        val count = withContext(Dispatchers.IO) {
            getWatchVideoUseCase.checkWatchVideoExisted(watchVideo.id)
        }

        if (count == 0) {
            withContext(Dispatchers.IO) {
                getWatchVideoUseCase.insertWatchVideo(watchVideo)
            }
        }
    }

}

package com.client.app.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.client.app.data.database.entities.WatchedVideo
import com.client.app.domain.entities.Video
import com.client.app.domain.usecases.CheckSearchHistoryExitedUseCase
import com.client.app.domain.usecases.CheckWatchedVideoExitedUseCase
import com.client.app.domain.usecases.GetVideoInfoUseCase
import com.client.app.domain.usecases.InsertSearchHistoryUseCase
import com.client.app.domain.usecases.InsertWatchedVideoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DetailViewModel(
    private val getVideoInfoUseCase: GetVideoInfoUseCase,
    private val checkWatchedVideoExitedUseCase: CheckWatchedVideoExitedUseCase,
    private val insertWatchedVideoUseCase: InsertWatchedVideoUseCase
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
    fun insertWatchVideo(watchedVideo: WatchedVideo) = viewModelScope.launch {
        val count = withContext(Dispatchers.IO) {
            checkWatchedVideoExitedUseCase.invoke(
                CheckWatchedVideoExitedUseCase.Param(watchedVideo.id)
            )
        }

        if (count == 0) {
            withContext(Dispatchers.IO) {
                insertWatchedVideoUseCase.invoke(
                    InsertWatchedVideoUseCase.Param(watchedVideo)
                )
            }
        }
    }

}

package com.client.app.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.client.app.domain.entities.Video
import com.client.app.domain.usecases.GetVideoInfoCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DetailViewModel(private val getVideoInfoCase: GetVideoInfoCase) : ViewModel() {
    private var videoId: LiveData<Int> = MediatorLiveData()
    val videoInfo: LiveData<Video> = MediatorLiveData<Video>().apply {
        addSource(videoId) {
            val msisdn = "0969633777"
            val timestamp = "123"
            val security = "123"
            val videoId = videoId.value ?: return@addSource
            viewModelScope.launch(Dispatchers.IO) {
                postValue(
                    getVideoInfoCase.invoke(
                        GetVideoInfoCase.Param(
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
}

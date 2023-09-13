package com.client.app.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import com.client.app.repository.VideoRepository
import com.client.app.entities.Video
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DetailViewModel(private val videoRepository: VideoRepository) : ViewModel() {
    private var id=0
    init {
        val msisdn = "0969633777"
        val timestamp = "123"
        val security = "123"
        viewModelScope.launch (Dispatchers.IO){
            videoRepository.getVideoDetail(id,msisdn, timestamp, security)
        }
    }
    fun setId(id:Int){
        this.id=id
    }
    val videoDetail:LiveData<Video>
        get() = videoRepository.videoDetailLive
}

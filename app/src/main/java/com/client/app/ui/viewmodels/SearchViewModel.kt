package com.client.app.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import com.client.app.repository.VideoRepository
import com.client.app.entities.Video
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(private val videoRepository: VideoRepository) : ViewModel() {
    fun search(key: String) {
        val msisdn = "0969633777"
        val timestamp = "123"
        val security = "123"
        val page = 0
        val size = 10
        val lastHashId = "13asd"
        viewModelScope.launch (Dispatchers.IO){
            videoRepository.getVideoSearch(msisdn, timestamp, security, page, size, key,lastHashId)
        }
    }
    val videoSearch:LiveData<List<Video>>
        get() = videoRepository.videoSearchLive
}

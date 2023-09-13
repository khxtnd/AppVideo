package com.client.app.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.client.app.repository.VideoRepository

class HomeViewModelFactory(private val videoRepository: VideoRepository): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(videoRepository) as T
    }
}
class SearchViewModelFactory(private val videoRepository: VideoRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchViewModel(videoRepository) as T
    }
}
class DetailViewModelFactory(private val videoRepository: VideoRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(videoRepository) as T
    }
}
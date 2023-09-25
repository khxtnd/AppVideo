package com.client.app.di

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.client.app.ui.detail.DetailViewModel
import com.client.app.ui.home.HomeViewModel
import com.client.app.ui.search.SearchViewModel

class HomeViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(getListVideoUseCase, application) as T
    }
}

class SearchViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchViewModel(searchListVideoUseCase, application) as T
    }
}

class DetailViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(getVideoInfoUseCase, application) as T
    }
}
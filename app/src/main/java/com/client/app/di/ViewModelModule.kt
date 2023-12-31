package com.client.app.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.client.app.ui.detail.DetailViewModel
import com.client.app.ui.home.HomeViewModel
import com.client.app.ui.search.SearchViewModel

class HomeViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(getListVideoUseCase,
            getAllWatchedVideoUseCase,
            deleteWatchedVideoUseCase) as T
    }
}

class SearchViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchViewModel(
            searchListVideoUseCase,
            deleteAllSearchHistoryUseCase,
            deleteSearchHistoryUseCase,
            checkSearchHistoryExitedUseCase,
            getAllSearchHistoryUseCase,
            insertSearchHistoryUseCase
        ) as T
    }
}

class DetailViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(getVideoInfoUseCase,
            checkWatchedVideoExitedUseCase,
            insertWatchedVideoUseCase) as T
    }
}
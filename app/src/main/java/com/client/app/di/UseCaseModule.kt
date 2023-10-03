package com.client.app.di

import com.client.app.domain.usecases.CheckSearchHistoryExitedUseCase
import com.client.app.domain.usecases.DeleteAllSearchHistoryUseCase
import com.client.app.domain.usecases.DeleteSearchHistoryUseCase
import com.client.app.domain.usecases.GetAllSearchHistoryUseCase
import com.client.app.domain.usecases.GetListVideoUseCase
import com.client.app.domain.usecases.GetVideoInfoUseCase
import com.client.app.domain.usecases.GetWatchVideoUseCase
import com.client.app.domain.usecases.InsertSearchHistoryUseCase
import com.client.app.domain.usecases.SearchListVideoUseCase

val getListVideoUseCase by lazy {
    GetListVideoUseCase(videoRepository)
}
val getVideoInfoUseCase by lazy {
    GetVideoInfoUseCase(videoRepository)
}

val searchListVideoUseCase by lazy {
    SearchListVideoUseCase(videoRepository)
}
val getAllSearchHistoryUseCase by lazy {
    GetAllSearchHistoryUseCase(searchHistoryRepository)
}
val deleteAllSearchHistoryUseCase by lazy {
    DeleteAllSearchHistoryUseCase(searchHistoryRepository)
}

val deleteSearchHistoryUseCase by lazy {
    DeleteSearchHistoryUseCase(searchHistoryRepository)
}

val insertSearchHistoryUseCase by lazy {
    InsertSearchHistoryUseCase(searchHistoryRepository)
}
val checkSearchHistoryExitedUseCase by lazy {
    CheckSearchHistoryExitedUseCase(searchHistoryRepository)
}
val getWatchVideoUseCase by lazy {
    GetWatchVideoUseCase(watchVideoRepository)
}
package com.client.app.di

import com.client.app.domain.usecases.GetListVideoUseCase
import com.client.app.domain.usecases.GetVideoInfoUseCase
import com.client.app.domain.usecases.GetWatchVideoUseCase
import com.client.app.domain.usecases.SearchHistoryUseCase
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
val searchHistoryUseCase by lazy {
    SearchHistoryUseCase(searchHistoryRepository)
}
val getWatchVideoUseCase by lazy {
    GetWatchVideoUseCase(watchVideoRepository)
}
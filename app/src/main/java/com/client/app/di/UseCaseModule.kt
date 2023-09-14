package com.client.app.di

import com.client.app.domain.usecases.GetListVideoUseCase
import com.client.app.domain.usecases.GetVideoInfoCase
import com.client.app.domain.usecases.SearchListVideoUseCase

val getListVideoUseCase by lazy {
    GetListVideoUseCase(videoRepository)
}
val getVideoInfoUseCase by lazy {
    GetVideoInfoCase(videoRepository)
}

val searchListVideoUseCase by lazy {
    SearchListVideoUseCase(videoRepository)
}
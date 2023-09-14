package com.client.app.domain.usecases

import com.client.app.domain.entities.Video
import com.client.app.domain.repositories.VideoRepository


class SearchListVideoUseCase(private val videoRepository: VideoRepository) :
    BaseUseCase<SearchListVideoUseCase.Param, List<Video>> {


    data class Param(
        val msisdn: String,
        val timestamp: String,
        val security: String,
        val page: Int,
        val size: Int,
        val keySearch: String,
        val lastHashId: String
    )

    override suspend fun invoke(param: Param): List<Video> {
        return videoRepository.searchListVideo(
            param.msisdn,
            param.timestamp,
            param.security,
            param.page,
            param.size,
            param.keySearch,
            param.lastHashId
        )
    }
}
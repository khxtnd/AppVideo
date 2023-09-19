package com.client.app.domain.usecases

import android.util.Log
import com.client.app.domain.entities.Video
import com.client.app.domain.repositories.VideoRepository


class GetListVideoUseCase(private val videoRepository: VideoRepository) :
    BaseUseCase<GetListVideoUseCase.Param, List<Video>> {


    data class Param(
        val msisdn: String,
        val timestamp: String,
        val security: String,
        val page: Int,
        val size: Int,
        val lastHashId: String
    )

    override suspend fun invoke(param: Param): List<Video> {
        return videoRepository.getVideoHot(
            param.msisdn,
            param.timestamp,
            param.security,
            param.page,
            param.size,
            param.lastHashId
        )
    }
}
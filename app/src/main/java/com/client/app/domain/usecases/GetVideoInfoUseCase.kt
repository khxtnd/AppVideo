package com.client.app.domain.usecases

import com.client.app.domain.entities.Video
import com.client.app.domain.repositories.VideoRepository


class GetVideoInfoUseCase(private val videoRepository: VideoRepository) :
    BaseUseCase<GetVideoInfoUseCase.Param, Video> {


    data class Param(
        val id: Int,
        val msisdn: String,
        val timestamp: String,
        val security: String,
    )

    override suspend fun invoke(param: Param): Video {
        return videoRepository.getVideoDetail(
            param.id,
            param.msisdn,
            param.timestamp,
            param.security
        )
    }
}
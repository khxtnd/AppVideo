package com.client.app.domain.entities

data class Video(
    val id: Int,
    val isLike: Int,
    val videoImage: String,
    val videoMedia: String,
    val videoTime: String,
    val videoTitle: String
)

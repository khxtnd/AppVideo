package com.client.app.entities

data class Video(
    val id: Int,
    val isLike: Int,
    val videoImage: String,
    val videoMedia: String,
    val videoTime: Int,
    val videoTitle: String,
    val totalViews: Int,
    val totalLikes: Int,
    val totalShares: Int,
    val totalComments: Int,
    val channel: Channel?,
    val list_resolution: List<Resolution>?
)

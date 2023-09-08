package com.client.app.domain.entities

data class Channel(
    val channelAvatar: String,
    val channelName: String,
    val createdFrom: Any,
    val description: Any,
    val headerBanner: Any,
    val id: Int,
    val isFollow: Int,
    val isOfficial: Any,
    val isOwner: Int,
    val numFollows: Int,
    val numVideos: Int,
    val state: Any,
    val url: Any
)
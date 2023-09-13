package com.client.app.entities

data class DataListApi(
    val code: Any,
    val data: List<Video>,
    val desc: Any
)

data class DataDetailApi(
    val code: Any,
    val data: Video,
    val desc: Any
)
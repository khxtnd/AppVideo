package com.client.app.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.client.app.data.ApiInterface
import com.client.app.entities.DataDetailApi
import com.client.app.entities.DataListApi
import com.client.app.entities.Video
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VideoRepository(private val apiInterface: ApiInterface) {
    private val listVideoHot = MutableLiveData<List<Video>>()
    private val listVideoSearch = MutableLiveData<List<Video>>()
    private val videoDetail = MutableLiveData<Video>()
    val videoHotLive: LiveData<List<Video>>
        get() = listVideoHot
    val videoSearchLive: LiveData<List<Video>>
        get() = listVideoSearch
    val videoDetailLive: LiveData<Video>
        get() = videoDetail

    suspend fun getVideoHot(
        msisdn: String,
        timestamp: String,
        security: String,
        page: Int,
        size: Int,
        lastHashId: String
    ) {
        val result = apiInterface.getVideoHot(msisdn, timestamp, security, page, size, lastHashId)
        result.enqueue(object : Callback<DataListApi> {
            override fun onResponse(call: Call<DataListApi>, response: Response<DataListApi>) {
                if (response.isSuccessful) {
                    val dataApi = response.body()
                    dataApi?.let {
                        val videoList = it.data.map { data ->
                            Video(
                                data.id,
                                0,
                                data.videoImage,
                                "",
                                data.videoTime,
                                data.videoTitle,
                                0, 0, 0, 0,
                                null,
                                null
                            )
                        }
                        listVideoHot.postValue(videoList)
                    }

                }
            }

            override fun onFailure(call: Call<DataListApi>, t: Throwable) {
            }
        })
    }

    suspend fun getVideoSearch(
        msisdn: String,
        timestamp: String,
        security: String,
        page: Int,
        size: Int,
        q: String,
        lastHashId: String
    ) {
        val result =
            apiInterface.getVideoSearch(msisdn, timestamp, security, page, size, q, lastHashId)
        result.enqueue(object : Callback<DataListApi> {
            override fun onResponse(call: Call<DataListApi>, response: Response<DataListApi>) {
                if (response.isSuccessful) {
                    val dataApi = response.body()
                    dataApi?.let {
                        val videoList = it.data.map { data ->
                            Video(
                                data.id,
                                0,
                                data.videoImage,
                                "",
                                data.videoTime,
                                data.videoTitle,
                                0, 0, 0, 0,
                                null,
                                null
                            )
                        }
                        listVideoSearch.postValue(videoList)
                    }
                }
            }

            override fun onFailure(call: Call<DataListApi>, t: Throwable) {
            }
        })
    }

    suspend fun getVideoDetail(
        id: Int,
        msisdn: String,
        timestamp: String,
        security: String,
    ) {
        val result = apiInterface.getVideoDetail(id, msisdn, timestamp, security)
        result.enqueue(object : Callback<DataDetailApi> {
            override fun onResponse(call: Call<DataDetailApi>, response: Response<DataDetailApi>) {
                if (response.isSuccessful) {
                    val dataApi = response.body()
                    dataApi?.let {
                        val data = it.data
                        val video = Video(
                            data.id,
                            data.isLike,
                            data.videoImage,
                            data.videoMedia,
                            data.videoTime,
                            data.videoTitle,
                            data.totalViews,
                            data.totalLikes,
                            data.totalShares,
                            data.totalComments,
                            data.channel,
                            data.list_resolution
                        )
                        videoDetail.postValue(video)
                    }

                }
            }

            override fun onFailure(call: Call<DataDetailApi>, t: Throwable) {
            }
        })
    }
}
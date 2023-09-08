package com.client.app.ui.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.client.app.data.api.MyRetrofitClient
import com.client.app.data.api.VideoSearchApi
import com.client.app.databinding.ActivitySearchBinding
import com.client.app.domain.entities.DataListApi
import com.client.app.domain.entities.Video
import com.client.app.ui.adapters.VideoListAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvBackSa.setOnClickListener {
            finish()
        }
        binding.searchViewSa.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                setupVideoSearch(newText)
                return true
            }

        })
    }

    private fun setupVideoSearch(q: String) {

        val retrofit = MyRetrofitClient.createRetrofit()
        val videoService = retrofit.create(VideoSearchApi::class.java)

        val msisdn = "0969633777"
        val timestamp = "123"
        val security = "123"
        val page = 0
        val size = 10
        val lastHashId = "13asd"

        val call =
            videoService.getVideoSearch(msisdn, timestamp, security, page, size, q, lastHashId)
        call.enqueue(object : Callback<DataListApi> {
            override fun onResponse(call: Call<DataListApi>, response: Response<DataListApi>) {
                if (response.isSuccessful) {
                    val dataApi = response.body()
                    dataApi?.let {
                        val videoSearch = it.data.map { data ->
                            Video(
                                data.id,
                                0,
                                data.videoImage,
                                "",
                                data.videoTime,
                                data.videoTitle,
                                0,0,0,0,
                                null, null
                            )
                        }
                        val adapter = VideoListAdapter(onItemClick)
                        adapter.setVideoList(videoSearch)
                        binding.recSa.layoutManager = LinearLayoutManager(this@SearchActivity)
                        binding.recSa.adapter = adapter
                    }

                } else {
                }
            }

            override fun onFailure(call: Call<DataListApi>, t: Throwable) {
            }
        })
    }

    private val onItemClick: (Video) -> Unit = {
        val intent = Intent(this@SearchActivity, DetailActivity::class.java)
        intent.putExtra("ID_VIDEO", it.id)
        startActivity(intent)
    }
}
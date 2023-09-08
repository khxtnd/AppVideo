package com.client.app.ui.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.client.app.R
import com.client.app.data.api.MyRetrofitClient
import com.client.app.data.api.VideoListApi
import com.client.app.databinding.ActivityHomeBinding
import com.client.app.domain.entities.DataListApi
import com.client.app.domain.entities.Video
import com.client.app.ui.adapters.VideoListAdapter
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private var gso: GoogleSignInOptions? = null
    private var gsc: GoogleSignInClient? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        gsc = GoogleSignIn.getClient(this, gso!!)

        binding.ivSearchHa.setOnClickListener {
            val intent = Intent(this@HomeActivity, SearchActivity::class.java)
            startActivity(intent)
        }
        binding.civProfileHa.setOnClickListener {
            val intent = Intent(this@HomeActivity, ProfileActivity::class.java)
            startActivity(intent)
        }

        val acct = GoogleSignIn.getLastSignedInAccount(this)
        val photoUrl = acct?.photoUrl

        if (photoUrl != null) {
            Glide.with(this)
                .load(photoUrl)
                .apply(RequestOptions().error(R.drawable.ic_user))
                .into(binding.civProfileHa)
        } else {
            Glide.with(this)
                .load(R.drawable.ic_user)
                .into(binding.civProfileHa)
        }

        setupVideoList()
    }

    private fun setupVideoList() {

        val retrofit = MyRetrofitClient.createRetrofit()
        val videoService = retrofit.create(VideoListApi::class.java)

        val msisdn = "0969633777"
        val timestamp = "123"
        val security = "123"
        val page = 0
        val size = 10
        val lastHashId = "13asd"

        val call = videoService.getVideos(msisdn, timestamp, security, page, size, lastHashId)
        call.enqueue(object : Callback<DataListApi> {
            override fun onResponse(call: Call<DataListApi>, response: Response<DataListApi>) {
                if (response.isSuccessful) {
                    val dataApi = response.body()
                    dataApi?.let {
                        val videoList = it.data.map { data ->
                            Video(
                                data.id,
                                data.isLike,
                                data.videoImage,
                                data.videoMedia,
                                data.videoTime,
                                data.videoTitle
                            )
                        }
                        val adapter = VideoListAdapter(onItemClick)
                        adapter.setVideoList(videoList)
                        binding.recHa.layoutManager = LinearLayoutManager(this@HomeActivity)
                        binding.recHa.adapter = adapter
                    }

                } else {
                }
            }

            override fun onFailure(call: Call<DataListApi>, t: Throwable) {
            }
        })
    }

    private val onItemClick: (Video) -> Unit = {
        val intent = Intent(this@HomeActivity, DetailActivity::class.java)
        intent.putExtra("ID_VIDEO", it.id)
        startActivity(intent)
    }

}




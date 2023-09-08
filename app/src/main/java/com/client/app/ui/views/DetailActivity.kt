package com.client.app.ui.views

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.client.app.R
import com.client.app.data.api.MyRetrofitClient
import com.client.app.data.api.VideoDetailApi
import com.client.app.databinding.ActivityDetailBinding
import com.client.app.domain.entities.DataDetailApi
import com.client.app.domain.entities.Video
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private var id = 0
    private var player: SimpleExoPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ivBackDa.setOnClickListener {
            finish()
        }
        id = intent.getIntExtra("ID_VIDEO", 0)
        setupVideoDetail()

    }

    private fun setupVideoDetail() {

        val retrofit = MyRetrofitClient.createRetrofit()
        val videoService = retrofit.create(VideoDetailApi::class.java)

        val msisdn = "0969633777"
        val timestamp = "123"
        val security = "123"

        val call = videoService.getVideoDetail(id, msisdn, timestamp, security)
        call.enqueue(object : Callback<DataDetailApi> {
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
                            data.videoTitle
                        )
                        binding.tvVideoTitleDa.text = video.videoTitle
                        setExoPlayer(video.videoMedia)

                    }

                } else {
                }
            }

            override fun onFailure(call: Call<DataDetailApi>, t: Throwable) {
            }
        })
    }

    private fun setExoPlayer(linkVideo: String) {
        player = SimpleExoPlayer.Builder(this).build()
        binding.playerView.player = player

        val mediaSource = buildMediaSource(linkVideo)

        Log.e("ExoPlayer1", linkVideo)

        player?.setMediaSource(mediaSource)
        player?.prepare()
        player?.play()
    }

    private fun buildMediaSource(linkVideo: String): MediaSource {
        val dataSourceFactory: DataSource.Factory =
            DefaultDataSourceFactory(this, "exoplayer-sample")
        return HlsMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(Uri.parse(linkVideo)))
    }

    override fun onDestroy() {
        super.onDestroy()
        player?.release()
        player = null
    }

}
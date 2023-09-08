package com.client.app.ui.views

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.client.app.R
import com.client.app.data.api.MyRetrofitClient
import com.client.app.data.api.VideoDetailApi
import com.client.app.databinding.ActivityDetailBinding
import com.client.app.domain.entities.DataDetailApi
import com.client.app.domain.entities.Video
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private var player: ExoPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = intent.getIntExtra("ID_VIDEO", 0)
        setupDataApiDA(this,id)
        setActionDA()
    }

    private fun setActionDA(){
        binding.ivBackDa.setOnClickListener {
            finish()
        }
    }
    private fun setupDataApiDA(context: Context,id:Int) {
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
                            data.videoTitle,
                            data.totalViews,
                            data.totalLikes,
                            data.totalShares,
                            data.totalComments,
                            data.channel,
                            data.list_resolution
                        )
                        setViewDA(context,video)
                    }
                }
            }

            override fun onFailure(call: Call<DataDetailApi>, t: Throwable) {
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        player?.release()
        player = null
    }

    @SuppressLint("SetTextI18n")
    private fun setViewDA(context: Context, video: Video){
        Glide.with(context)
            .load(video.channel?.channelAvatar)
            .apply(RequestOptions().error(R.drawable.ic_channel_40))
            .into(binding.civChannelImageDa)
        binding.tvVideoTitleDa.text = video.videoTitle
        binding.tvChannelNameDa.text=video.channel?.channelName
        binding.tvTotalLikesDa.text=convertNumber(video.totalLikes)+" likes"
        binding.tvTotalCommentsDa.text=convertNumber(video.totalComments)+ " comments"
        binding.tvTotalSharesDa.text=convertNumber(video.totalShares)+ " shares"
        binding.tvTotalViewsDa.text=convertNumber(video.totalViews)+ " views"
        setExoPlayerDA(video.videoMedia)
    }
    private fun  convertNumber(number:Int):String{
        if(number>1000){
            return (number/1000).toString()+"K"
        }
        return number.toString()
    }
    private fun setExoPlayerDA(linkVideo: String) {
        player = ExoPlayer.Builder(this).build()
        binding.playerView.player = player
        val mediaItem = MediaItem.fromUri(linkVideo)

        player!!.setMediaItem(mediaItem)
        player!!.prepare()
    }

}
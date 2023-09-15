package com.client.app.ui.detail

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.client.app.R
import com.client.app.databinding.ActivityDetailBinding
import com.client.app.databinding.CustomControllerBinding
import com.client.app.di.DetailViewModelFactory

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by lazy {
        ViewModelProvider(this, DetailViewModelFactory())[DetailViewModel::class.java]
    }
    private lateinit var player:ExoPlayer

    private var isFullScreen = false


    private lateinit var ivPlayDa :ImageView
    private lateinit var ivReplayDa :ImageView
    private lateinit var ivForwardDa :ImageView
    private lateinit var ivFullScreenDa:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = intent.getIntExtra("ID_VIDEO", 0)
        setViewDA(id)
        setActionDA()
        initControlExo()

        setControlExo()

    }

    private fun initControlExo() {
        ivPlayDa=findViewById(R.id.iv_play_da)
        ivForwardDa=findViewById(R.id.iv_forward_da)
        ivReplayDa=findViewById(R.id.iv_replay_da)
        ivFullScreenDa=findViewById(R.id.iv_fullScreen_da)
    }


    private fun setViewDA(id:Int) {
        detailViewModel.setId(id)
        detailViewModel.videoInfo.observe(this) {
            Glide.with(this)
                .load(it.channel?.channelAvatar)
                .apply(RequestOptions().error(R.drawable.ic_channel_40))
                .into(binding.civChannelImageDa)
            binding.tvVideoTitleDa.text = it.videoTitle
            binding.tvChannelNameDa.text = it.channel?.channelName
            binding.tvTotalLikesDa.text = convertNumber(it.totalLikes) + " likes"
            binding.tvTotalCommentsDa.text = convertNumber(it.totalComments) + " comments"
            binding.tvTotalSharesDa.text = convertNumber(it.totalShares) + " shares"
            binding.tvTotalViewsDa.text = convertNumber(it.totalViews) + " views"
            setExoPlayerDA(it.videoMedia)
        }
    }
    private fun setControlExo() {
        val orientation = this.resources.configuration.orientation
       if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            ivFullScreenDa.setImageResource(R.drawable.ic_fullscreen_40)
        } else {
            ivFullScreenDa.setImageResource(R.drawable.ic_fullscreen_exit_40)
        }
        ivPlayDa.setOnClickListener {
            if(player.isPlaying){
                pauseVideo()
            }else{
                playVideo()
            }
        }
        ivFullScreenDa.setOnClickListener {
            requestedOrientation = if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            } else {
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
        }

    }

    private fun setActionDA() {
        binding.ivBackDa.setOnClickListener {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()

    }


    private fun convertNumber(number: Int): String {
        if (number > 1000) {
            return (number / 1000).toString() + "K"
        }
        return number.toString()
    }

    private fun setExoPlayerDA(linkVideo: String) {
        player = ExoPlayer.Builder(this).build()
        binding.playerView.player = player
        val mediaItem = MediaItem.fromUri(linkVideo)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }

    private fun playVideo(){
        ivPlayDa.setImageResource(R.drawable.ic_pause_circle_outline_60)
        player.play()
    }
    private fun pauseVideo(){
        ivPlayDa.setImageResource(R.drawable.ic_play_circle_outline_60)
        player.pause()
    }

}
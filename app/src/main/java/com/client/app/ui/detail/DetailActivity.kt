package com.client.app.ui.detail

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.client.app.R
import com.client.app.data.database.entities.WatchVideo
import com.client.app.databinding.ActivityDetailBinding
import com.client.app.di.DetailViewModelFactory

class DetailActivity : AppCompatActivity(), PopupMenu.OnMenuItemClickListener {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by lazy {
        ViewModelProvider(this, DetailViewModelFactory())[DetailViewModel::class.java]
    }
    private lateinit var player:ExoPlayer

    private lateinit var ivPlayCustomExo :ImageView
    private lateinit var ivReplayCustomExo :ImageView
    private lateinit var ivForwardCustomExo :ImageView
    private lateinit var ivFullScreenCustomExo:ImageView
    private lateinit var ivBackCustomExo:ImageView
    private lateinit var ivSettingCustomExo:ImageView
    private lateinit var tvTitleCustomExo:TextView
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
        ivPlayCustomExo=findViewById(R.id.iv_play_custom_exo)
        ivForwardCustomExo=findViewById(R.id.iv_forward_custom_exo)
        ivReplayCustomExo=findViewById(R.id.iv_replay_custom_exo)
        ivFullScreenCustomExo=findViewById(R.id.iv_fullScreen_da)
        ivBackCustomExo=findViewById(R.id.iv_back_custom_exo)
        tvTitleCustomExo=findViewById(R.id.tv_title_custom_exo)
        ivSettingCustomExo=findViewById(R.id.iv_setting_custom_exo)
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
            val watchVideo= WatchVideo(it.id,it.videoTime,it.videoTitle,it.videoImage)
            detailViewModel.insertWatchVideo(watchVideo)
        }
    }
    private fun setControlExo() {
        val orientation = this.resources.configuration.orientation
       if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            ivFullScreenCustomExo.setImageResource(R.drawable.ic_fullscreen_40)
            ivBackCustomExo.visibility=View.GONE
            tvTitleCustomExo.visibility=View.GONE
        } else {
            ivFullScreenCustomExo.setImageResource(R.drawable.ic_fullscreen_exit_40)
           detailViewModel.videoInfo.observe(this) {
              tvTitleCustomExo.text=it.videoTitle
           }

        }
        ivPlayCustomExo.setOnClickListener {
            if(player.isPlaying){
                pauseVideo()
            }else{
                playVideo()
            }
        }
        ivFullScreenCustomExo.setOnClickListener {
            requestedOrientation = if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            } else {
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
            detailViewModel.setCurrentPosition(player.currentPosition)
        }
        ivBackCustomExo.setOnClickListener {
            finish()
        }

        ivForwardCustomExo.setOnClickListener {
            if(player.currentPosition+10000>=player.duration){
                player.seekTo(0)
            }else{
                player.seekTo(player.currentPosition+10000)
            }
        }
        ivReplayCustomExo.setOnClickListener {
            if(player.currentPosition-10000<=0){
                player.seekTo(0)
            }else{
                player.seekTo(player.currentPosition-10000)
            }
        }
        ivSettingCustomExo.setOnClickListener {
            player.pause()
            ivPlayCustomExo.setImageResource(R.drawable.ic_play_circle_outline_60)
            showMenuSetting(it)
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
        ivPlayCustomExo.setImageResource(R.drawable.ic_pause_circle_outline_60)
        player.seekTo(detailViewModel.getCurrentPosition())
        player.addListener(object : Player.Listener{
            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                if(playbackState == Player.STATE_ENDED) {
                    player.seekTo(0)
                    player.play()
                }
            }
        })
        Log.e("setQaVideo",linkVideo)


    }

    private fun playVideo(){
        ivPlayCustomExo.setImageResource(R.drawable.ic_pause_circle_outline_60)
        player.play()
    }
    private fun pauseVideo(){
        ivPlayCustomExo.setImageResource(R.drawable.ic_play_circle_outline_60)
        player.pause()
    }
    private fun showMenuSetting(v:View){
        val popupMenu=PopupMenu(this,v)
        popupMenu.setOnMenuItemClickListener(this)
        popupMenu.inflate(R.menu.setting_menu)
        popupMenu.show()
    }
    override fun onMenuItemClick(p0: MenuItem?): Boolean {
        when(p0?.itemId){
            R.id.set_144p->{
                setQaVideo(3)
            }
            R.id.set_240p->{
                setQaVideo(2)

            }
            R.id.set_360p->{
                setQaVideo(4)

            }
            R.id.set_480p->{
                setQaVideo(1)

            }
            R.id.set_720p->{
                setQaVideo(0)
            }
        }
        return true
    }

    private fun setQaVideo(position:Int){
        detailViewModel.videoInfo.observe(this) {
            val linkVideo= it.list_resolution?.get(position)?.video_path
            detailViewModel.setCurrentPosition(player.currentPosition)
            player.release()
            Log.e("setQaVideo",linkVideo.toString())
            linkVideo?.let { it1 -> setExoPlayerDA(it1) }
        }
    }
}
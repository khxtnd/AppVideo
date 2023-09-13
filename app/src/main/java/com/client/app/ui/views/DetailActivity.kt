package com.client.app.ui.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.client.app.R
import com.client.app.data.ApiInterface
import com.client.app.data.ApiUtilities
import com.client.app.databinding.ActivityDetailBinding
import com.client.app.repository.VideoRepository
import com.client.app.ui.viewmodels.DetailViewModel
import com.client.app.ui.viewmodels.DetailViewModelFactory

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private var player: ExoPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = intent.getIntExtra("ID_VIDEO", 0)
        setActionDA()
        setViewDA(id)
    }

    private fun setViewDA(id:Int) {
        val apiInterface = ApiUtilities.getInstance().create(ApiInterface::class.java)
        val videoRepository = VideoRepository(apiInterface)
        detailViewModel = ViewModelProvider(
            this,
            DetailViewModelFactory(videoRepository)
        )[DetailViewModel::class.java]
        detailViewModel.setId(id)
        detailViewModel.videoDetail.observe(this) {
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
            if (it.isLike == 1) {
                binding.ivIsLikeDa.setImageResource(R.drawable.ic_like3_40)
            }
            setExoPlayerDA(it.videoMedia)
        }
    }

    private fun setActionDA() {
        binding.ivBackDa.setOnClickListener {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        player?.release()
        player = null
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
        player!!.setMediaItem(mediaItem)
        player!!.prepare()
        player!!.play()
    }

}
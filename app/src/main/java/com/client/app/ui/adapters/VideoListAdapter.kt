package com.client.app.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.client.app.R
import com.client.app.databinding.ItemVideoBinding
import com.client.app.domain.entities.Video


class VideoListAdapter(
    private val onClick: (Video) -> Unit
) : RecyclerView.Adapter<VideoListViewHolder>() {
    private var list: List<Video> = listOf()

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoListViewHolder {
        val binding = ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoListViewHolder, position: Int) {
        val item = list[position]

        holder.binding.tvVideoTitleVi.text = item.videoTitle
        holder.binding.tvVideoTimeVi.text = item.videoTime

        Glide.with(holder.binding.ivVi)
            .load(item.videoImage)
            .transform(RoundedCorners(16))
            .apply(RequestOptions().error(R.drawable.ic_video))
            .into(holder.binding.ivVi)
        holder.binding.layoutItemVideo.setOnClickListener {
            onClick(item)
        }

    }

    fun setVideoList(list: List<Video>) {
        this.list = list
        notifyDataSetChanged()
    }
}

class VideoListViewHolder(val binding: ItemVideoBinding) : RecyclerView.ViewHolder(binding.root)

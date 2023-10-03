package com.client.app.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.client.app.R
import com.client.app.data.database.entities.WatchedVideo
import com.client.app.databinding.ItemWatchVideoBinding


class WatchHistoryAdapter(
    private val onItemWatchVideoClick: (WatchedVideo) -> Unit,
    private val onDeleteItemWatchVideoClick: (WatchedVideo) -> Unit
) : RecyclerView.Adapter<WatchVideoListViewHolder>() {
    private var list: List<WatchedVideo> = listOf()
    private val MAX_LENGTH = 25;
    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchVideoListViewHolder {
        val binding =
            ItemWatchVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WatchVideoListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WatchVideoListViewHolder, position: Int) {
        val item = list[position]
        var title = item.title
        if (title.length > MAX_LENGTH) {
            title = title.subSequence(0, MAX_LENGTH).toString() + "...";
        }
        holder.binding.tvVideoTitleVi.text = title
        holder.binding.tvVideoTimeWvi.text = convertToMinutesSeconds(item.time)

        Glide.with(holder.binding.ivWvi)
            .load(item.image)
            .transform(RoundedCorners(16))
            .apply(RequestOptions().error(R.drawable.ic_video_40))
            .into(holder.binding.ivWvi)
        holder.binding.layoutItemWatchVideo.setOnClickListener {
            onItemWatchVideoClick(item)
        }
        holder.binding.ivDeleteWvi.setOnClickListener {
            onDeleteItemWatchVideoClick(item)
            Log.e("WatchAdapter","delete")
        }

    }

    fun setWatchVideoList(list: List<WatchedVideo>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun convertToMinutesSeconds(seconds: Int): String {
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return String.format("%02d:%02d", minutes, remainingSeconds)
    }

}

class WatchVideoListViewHolder(val binding: ItemWatchVideoBinding) :
    RecyclerView.ViewHolder(binding.root)

package com.client.app.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.client.app.data.database.entities.SearchHistory
import com.client.app.databinding.ItemSearchHistoryBinding


class SearchHistoryListAdapter(
    private val onItemClick: (SearchHistory) -> Unit,
    private val onDeleteClick: (SearchHistory) -> Unit
) : RecyclerView.Adapter<SearchHistoryListViewHolder>() {
    private var list: List<SearchHistory> = listOf()
    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryListViewHolder {
        val binding =
            ItemSearchHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchHistoryListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchHistoryListViewHolder, position: Int) {
        val item = list[position]
        holder.binding.tvKeySearchShi.text = item.keySearch
        holder.binding.tvKeySearchShi.setOnClickListener {
            onItemClick(item)
        }
        holder.binding.ivDeleteShi.setOnClickListener {
            onDeleteClick(item)
        }
    }

    fun setSearchHistoryList(list: List<SearchHistory>) {
        this.list = list
        notifyDataSetChanged()
    }
}

class SearchHistoryListViewHolder(val binding: ItemSearchHistoryBinding) :
    RecyclerView.ViewHolder(binding.root)

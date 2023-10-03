package com.client.app.ui.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.client.app.data.database.entities.SearchHistory
import com.client.app.databinding.ActivitySearchBinding
import com.client.app.di.SearchViewModelFactory
import com.client.app.ui.adapters.SearchHistoryListAdapter
import com.client.app.ui.adapters.VideoListAdapter
import com.client.app.ui.detail.DetailActivity
import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private val searchViewModel: SearchViewModel by lazy {
        ViewModelProvider(
            this,
            SearchViewModelFactory()
        )[SearchViewModel::class.java]
    }
    private var adapterVideo: VideoListAdapter? = null
    private var adapterHistory: SearchHistoryListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvBackSa.setOnClickListener {
            finish()
        }
        searchViewModel.getAllSearchHistory()

        binding.etSearchSa.doAfterTextChanged {
            if (it.isNullOrEmpty()) {
                binding.recVideoSa.visibility = View.GONE
                binding.linearHistorySa.visibility = View.VISIBLE
            } else {
                binding.recVideoSa.visibility = View.VISIBLE
                binding.linearHistorySa.visibility = View.GONE
                searchViewModel.search(it.toString())
            }
        }
        setRecycleViewHistorySa()
        binding.btDeleteAllSa.setOnClickListener {
            lifecycleScope.launch {
                searchViewModel.deleteAllSearchHistory()
            }

        }
        setRecycleViewVideoSa()
        observerData()
    }


    private fun setRecycleViewHistorySa() {
        adapterHistory = SearchHistoryListAdapter(onItemClick, onItemDelete)
        binding.recHistorySa.layoutManager = LinearLayoutManager(this@SearchActivity)
        binding.recHistorySa.adapter = adapterHistory
    }

    private fun setRecycleViewVideoSa() {
        adapterVideo = VideoListAdapter {
            val searchHistory = SearchHistory(it.videoTitle)
            searchViewModel.insertSearchHistory(searchHistory)
            val intent = Intent(this@SearchActivity, DetailActivity::class.java)
            intent.putExtra("ID_VIDEO", it.id)
            startActivity(intent)
            finish()
        }
        binding.recVideoSa.layoutManager = LinearLayoutManager(this@SearchActivity)
        binding.recVideoSa.adapter = adapterVideo
    }

    private fun observerData() = with(searchViewModel) {
        listVideo.observe(this@SearchActivity) {
            val adapter = adapterVideo ?: return@observe
            adapter.setVideoList(it)
        }
        listSearchHistory.observe(this@SearchActivity) {
            val adapter = adapterHistory ?: return@observe
            if (it.isNullOrEmpty()) {
                binding.btDeleteAllSa.visibility = View.GONE
            }
            adapter.setSearchHistoryList(it)
        }
    }

    private val onItemClick: (SearchHistory) -> Unit = {
        val keySearch = it.keySearch
        binding.etSearchSa.setText(keySearch)
        binding.etSearchSa.setSelection(keySearch.length)
    }
    private val onItemDelete: (SearchHistory) -> Unit = {
        searchViewModel.deleteSearchHistory(it)
    }
}
package com.client.app.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.client.app.data.database.entities.History
import com.client.app.databinding.ActivitySearchBinding
import com.client.app.di.SearchViewModelFactory
import com.client.app.ui.adapters.HistoryAdapter
import com.client.app.ui.adapters.VideoListAdapter
import com.client.app.ui.detail.DetailActivity
import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private val searchViewModel: SearchViewModel by lazy {
        ViewModelProvider(
            this,
            SearchViewModelFactory(this.application)
        )[SearchViewModel::class.java]
    }
    private var adapterVideo: VideoListAdapter? = null
    private var adapterHistory: HistoryAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvBackSa.setOnClickListener {
            finish()
        }

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
                searchViewModel.deleteAllHistory()
            }

        }
        setRecycleViewVideoSa()
        observerData()
    }


    private fun setRecycleViewHistorySa() {
        adapterHistory = HistoryAdapter (onItemClick,onItemDelete)
        binding.recHistorySa.layoutManager = LinearLayoutManager(this@SearchActivity)
        binding.recHistorySa.adapter = adapterHistory
    }

    private fun setRecycleViewVideoSa() {
        adapterVideo = VideoListAdapter {
            val history = History(it.id,it.videoTitle)
            searchViewModel.insertHistory(history)
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
        getAllHistory().observe(this@SearchActivity) {
            val adapter = adapterHistory ?: return@observe
            if(it.isNullOrEmpty()){
                binding.btDeleteAllSa.visibility=View.GONE
            }
            adapter.setHistoryList(it)
        }
    }
    private val onItemClick:(History)->Unit={
        val keySearch = it.keySearch
        binding.etSearchSa.setText(keySearch)
        binding.etSearchSa.setSelection(keySearch.length)
    }
    private val onItemDelete:(History)-> Unit={
        searchViewModel.deleteHistory(it)
    }
}
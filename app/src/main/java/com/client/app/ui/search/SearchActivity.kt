package com.client.app.ui.search

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.client.app.R
import com.client.app.databinding.ActivitySearchBinding
import com.client.app.di.SearchViewModelFactory
import com.client.app.ui.adapters.VideoListAdapter
import com.client.app.ui.detail.DetailActivity

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private val searchViewModel: SearchViewModel by lazy {
        ViewModelProvider(this, SearchViewModelFactory())[SearchViewModel::class.java]
    }
    private var adapter: VideoListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvBackSa.setOnClickListener {
            finish()
        }

        binding.etSearchSa.doAfterTextChanged {
            searchViewModel.search(it?.toString() ?: "")
        }
        setRecycleViewSa()
        observerData()
    }

    private fun setRecycleViewSa() {
        adapter = VideoListAdapter {
            val intent = Intent(this@SearchActivity, DetailActivity::class.java)
            intent.putExtra("ID_VIDEO", it.id)
            startActivity(intent)
            finish()
        }
        binding.recSa.layoutManager = LinearLayoutManager(this@SearchActivity)
        binding.recSa.adapter = adapter
    }

    private fun observerData() = with(searchViewModel) {
        listVideo.observe(this@SearchActivity) {
            val adapter = adapter ?: return@observe
            adapter.setVideoList(it)
        }
    }
}
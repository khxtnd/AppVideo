package com.client.app.ui.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.client.app.data.ApiInterface
import com.client.app.data.ApiUtilities
import com.client.app.databinding.ActivitySearchBinding
import com.client.app.entities.Video
import com.client.app.repository.VideoRepository
import com.client.app.ui.adapters.VideoListAdapter
import com.client.app.ui.viewmodels.SearchViewModel
import com.client.app.ui.viewmodels.SearchViewModelFactory

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var searchViewModel: SearchViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvBackSa.setOnClickListener {
            finish()
        }
        binding.searchViewSa.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                setRecycleViewSa(newText)
                return true
            }

        })

    }

    private fun setRecycleViewSa(keySearch:String) {
        val apiInterface = ApiUtilities.getInstance().create(ApiInterface::class.java)
        val videoRepository = VideoRepository(apiInterface)
        val adapter = VideoListAdapter(onItemClick)
        binding.recSa.layoutManager = LinearLayoutManager(this@SearchActivity)
        binding.recSa?.adapter = adapter
        searchViewModel = ViewModelProvider(
            this,
            SearchViewModelFactory(videoRepository)
        )[SearchViewModel::class.java]
        searchViewModel.search(keySearch)
        searchViewModel.videoSearch.observe(this) {
            adapter.setVideoList(it)
        }
    }

    private val onItemClick: (Video) -> Unit = {
        val intent = Intent(this@SearchActivity, DetailActivity::class.java)
        intent.putExtra("ID_VIDEO", it.id)
        startActivity(intent)
        finish()
    }
}
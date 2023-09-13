package com.client.app.ui.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.client.app.R
import com.client.app.data.ApiInterface
import com.client.app.data.ApiUtilities
import com.client.app.databinding.ActivityHomeBinding
import com.client.app.entities.Video
import com.client.app.repository.VideoRepository
import com.client.app.ui.adapters.VideoListAdapter
import com.client.app.ui.viewmodels.HomeViewModel
import com.client.app.ui.viewmodels.HomeViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private var gso: GoogleSignInOptions? = null
    private var gsc: GoogleSignInClient? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        gsc = GoogleSignIn.getClient(this, gso!!)

        binding.ivSearchHa.setOnClickListener {
            val intent = Intent(this@HomeActivity, SearchActivity::class.java)
            startActivity(intent)
        }
        binding.civProfileHa.setOnClickListener {
            val intent = Intent(this@HomeActivity, ProfileActivity::class.java)
            startActivity(intent)
        }

        val acct = GoogleSignIn.getLastSignedInAccount(this)
        val photoUrl = acct?.photoUrl

        if (photoUrl != null) {
            Glide.with(this)
                .load(photoUrl)
                .apply(RequestOptions().error(R.drawable.ic_user_40))
                .into(binding.civProfileHa)
        } else {
            Glide.with(this)
                .load(R.drawable.ic_user_40)
                .into(binding.civProfileHa)
        }
        setRecycleViewHa()
    }

    private fun setRecycleViewHa() {
        val apiInterface = ApiUtilities.getInstance().create(ApiInterface::class.java)
        val videoRepository = VideoRepository(apiInterface)
        val adapter = VideoListAdapter(onItemClick)
        binding.recHa.layoutManager = LinearLayoutManager(this@HomeActivity)
        binding.recHa?.adapter = adapter
        homeViewModel = ViewModelProvider(
            this,
            HomeViewModelFactory(videoRepository)
        )[HomeViewModel::class.java]
        homeViewModel.videoHot.observe(this) {
            adapter.setVideoList(it)
        }
    }

    private val onItemClick: (Video) -> Unit = {
        val intent = Intent(this@HomeActivity, DetailActivity::class.java)
        intent.putExtra("ID_VIDEO", it.id)
        startActivity(intent)
    }
}




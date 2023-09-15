package com.client.app.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.client.app.R
import com.client.app.databinding.ActivityHomeBinding
import com.client.app.di.HomeViewModelFactory
import com.client.app.ui.adapters.VideoListAdapter
import com.client.app.ui.detail.DetailActivity
import com.client.app.ui.search.SearchActivity
import com.client.app.ui.views.LoginActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val homeViewModel by lazy {
        ViewModelProvider(this, HomeViewModelFactory())[HomeViewModel::class.java]
    }

    private var adapter: VideoListAdapter? = null

    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var ivAvatarNav:ImageView
    private lateinit var tvUsernameNav:TextView

    private var gso: GoogleSignInOptions? = null
    private var gsc: GoogleSignInClient? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setTheme(R.style.actionBarTheme)
        setContentView(binding.root)
        toggle = ActionBarDrawerToggle(this, binding.root, R.string.open, R.string.close)
        binding.root.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setViewNav()

        setRecycleViewHa()
        observeData()

    }


    private fun setViewNav() {
        val headerView: View = binding.navView.getHeaderView(0)

        ivAvatarNav=headerView.findViewById(R.id.iv_avatar_Nav)
        tvUsernameNav=headerView.findViewById(R.id.tv_username_Nav)
        gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        gsc = GoogleSignIn.getClient(this, gso!!)

        val acct = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
            val personName = acct.displayName
            tvUsernameNav.text = personName
        }


        val photoUrl = acct?.photoUrl
        if (photoUrl != null) {
            Glide.with(this)
                .load(photoUrl)
                .apply(RequestOptions().error(R.drawable.ic_user_40))
                .into(ivAvatarNav)
        }
        binding.navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.logoutNav ->{
                    gsc!!.signOut().addOnCompleteListener {
                        val intent = Intent(this@HomeActivity, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }
                }
            }
            return@setNavigationItemSelectedListener true
        }
    }

    private fun setRecycleViewHa() {
        adapter = VideoListAdapter {
            val intent = Intent(this@HomeActivity, DetailActivity::class.java)
            intent.putExtra("ID_VIDEO", it.id)
            startActivity(intent)
        }
        binding.recHa.layoutManager = LinearLayoutManager(this@HomeActivity)
        binding.recHa.adapter = adapter

    }

    private fun observeData() = with(homeViewModel) {
        videoHot.observe(this@HomeActivity) {
            val adapter = adapter ?: return@observe
            adapter.setVideoList(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search_actionbar,menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item))
            return true
        val id=item.itemId
        if(id==R.id.btnSearchActionBar){
            val intent = Intent(this@HomeActivity, SearchActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}




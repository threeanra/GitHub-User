package com.example.simplegithubuser.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.simplegithubuser.R
import com.example.simplegithubuser.adapter.SectionsPagerAdapter
import com.example.simplegithubuser.databinding.ActivityDetailBinding
import com.example.simplegithubuser.local.FavoriteUser
import com.example.simplegithubuser.network.response.DetailResponse
import com.example.simplegithubuser.viewmodel.FavoriteViewModel
import com.example.simplegithubuser.viewmodel.MainViewModel
import com.example.simplegithubuser.viewmodel.ViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    private val viewModel: MainViewModel by viewModels()

    private val viewModel2 by viewModels<FavoriteViewModel>{
        ViewModelFactory.getInstance(application)
    }

    lateinit var userName: String

    companion object {
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )

        const val EXTRA_DATA = "extra_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // get data from intent on UserAdapter
        userName = intent.getStringExtra(EXTRA_DATA).toString()

        // set data and get data detail from viewmodel
        viewModel.detailUser(userName)

        // change with observe from variabel userDetail and set data from this change with fun setUserDetail
        viewModel.detailUser.observe(this){ data ->
            showLoading(true)
            setUserDetail(data)

        }

        // change with observe from variabel isloading
        viewModel.isLoading.observe(this){
            showLoading(it)
        }

        // connect viewpager2 with adapter
        val sectionPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPagerAdapter


        // connect viewpager2 with TabLayoutMediator
        val tabs = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position->
            tab.text = resources.getString(TAB_TITLES[position])
            sectionPagerAdapter.username = userName
        }.attach()

        supportActionBar?.elevation = 0f


    }

    // set data user detail to view
    private fun setUserDetail(data: DetailResponse){
        binding.nameUser.text = data.login
        with(Glide.with(this)) {
            load(data.avatarUrl)
                .into(binding.imageUser)
        }
        binding.tvFollowersValue.text = data.followers.toString()
        binding.tvFollowingValue.text = data.following.toString()
        binding.usernameUser.text = data.name

        viewModel2.getFavoriteUserByUsername().observe(this@DetailActivity) { favoriteUsers ->
            var isFavorite = false
            for (favorite in favoriteUsers) {
                if (favorite.username == userName) {
                    isFavorite = true
                    break
                }
            }
            binding.favAdd.setImageResource(if (isFavorite) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24)

            binding.favAdd.setOnClickListener {
                if (isFavorite) {
                    viewModel2.deleteFavorite(FavoriteUser(data.login, data.avatarUrl))
                    Toast.makeText(this, "User dihapus dari daftar favorite", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel2.insertFavorite(FavoriteUser(data.login, data.avatarUrl))
                    Toast.makeText(this, "User ditambah ke daftar favorite", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}
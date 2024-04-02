package com.example.simplegithubuser.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simplegithubuser.adapter.FavoriteAdapter
import com.example.simplegithubuser.databinding.ActivityFavoriteBinding
import com.example.simplegithubuser.local.FavoriteUser
import com.example.simplegithubuser.viewmodel.FavoriteViewModel
import com.example.simplegithubuser.viewmodel.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val viewModel by viewModels<FavoriteViewModel>{
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getAllFavorite().observe(this) { favoritUserList ->
            if (favoritUserList != null) {
                getFavData(favoritUserList)
            }
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.layoutManager = layoutManager

    }

    private fun getFavData(items: List<FavoriteUser>) {
        val adapter = FavoriteAdapter()
        adapter.submitList(items)
        binding.rvFavorite.adapter = adapter
    }

}
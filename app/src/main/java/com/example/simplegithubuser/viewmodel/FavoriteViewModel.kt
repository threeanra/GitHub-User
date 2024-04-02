package com.example.simplegithubuser.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.simplegithubuser.local.FavoriteUser
import com.example.simplegithubuser.repository.FavoriteRepository

class FavoriteViewModel (application: Application) : ViewModel() {
    private val repoFav: FavoriteRepository = FavoriteRepository(application)

    init {
        getAllFavorite()
    }

    fun getAllFavorite(): LiveData<List<FavoriteUser>> = repoFav.getAllFavorite()

    fun insertFavorite(favorite: FavoriteUser){
        repoFav.insert(favorite)
    }

    fun deleteFavorite(favorite: FavoriteUser){
        repoFav.deleteFavorite(favorite)
    }
    fun getFavoriteUserByUsername(): LiveData<List<FavoriteUser>> = repoFav.getFavoriteUserByUsername()

}
package com.example.simplegithubuser.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.simplegithubuser.local.FavoriteDao
import com.example.simplegithubuser.local.FavoriteDatabase
import com.example.simplegithubuser.local.FavoriteUser
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    private val favoriteDao: FavoriteDao

    init {
        val database = FavoriteDatabase.getInstance(application)
        favoriteDao = database.favoriteDao
    }

    fun insert(favorite: FavoriteUser) {
        executorService.execute { favoriteDao.insert(favorite) }
    }
    fun getAllFavorite(): LiveData<List<FavoriteUser>> = favoriteDao.getAllFavorites()

    fun deleteFavorite(favorite: FavoriteUser) {
        executorService.execute { favoriteDao.delete(favorite) }
    }

    fun getFavoriteUserByUsername(): LiveData<List<FavoriteUser>> = favoriteDao.getAllFavorites()
}
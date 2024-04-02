package com.example.simplegithubuser.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite: FavoriteUser)

    @Query("SELECT * FROM user_favorite")
    fun getAllFavorites(): LiveData<List<FavoriteUser>>

    @Query("SELECT EXISTS(SELECT * FROM user_favorite WHERE username = :username)")
    fun getFavoriteByUsername(username: String): LiveData<Boolean>

    @Delete
    fun delete(favorite: FavoriteUser)
}
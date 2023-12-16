package com.example.unsplashwallpapers.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.unsplashwallpapers.Models.FavoritePhotos

@Dao
interface PhotoDao {

    @Insert
    suspend fun savePhoto(favoritePhotos: FavoritePhotos)

    @Query("SELECT * FROM favorite_photos")
    fun getAllFavPhotos(): LiveData<List<FavoritePhotos>>

}
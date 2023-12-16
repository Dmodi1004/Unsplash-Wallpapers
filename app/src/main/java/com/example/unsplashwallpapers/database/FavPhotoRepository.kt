package com.example.unsplashwallpapers.database

import androidx.lifecycle.LiveData
import com.example.unsplashwallpapers.Models.FavoritePhotos
import com.example.unsplashwallpapers.Models.Photo

class FavPhotoRepository(private val photoDao: PhotoDao) {

    val favoritePhoto: LiveData<List<FavoritePhotos>> = photoDao.getAllFavPhotos()

    suspend fun savePhoto(favoritePhotos: FavoritePhotos) {
        photoDao.savePhoto(favoritePhotos)
    }

}
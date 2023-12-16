package com.example.unsplashwallpapers.Models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.unsplashwallpapers.database.FavPhotoRepository
import com.example.unsplashwallpapers.database.FavPhotosDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val getAllFavPhotos: LiveData<List<FavoritePhotos>>
    private val repository: FavPhotoRepository

    init {
        val photoDao = FavPhotosDatabase.getDatabase(application).photoDao()
        repository = FavPhotoRepository(photoDao)
        getAllFavPhotos = repository.favoritePhoto
    }

    fun savePhoto(favoritePhotos: FavoritePhotos) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.savePhoto(favoritePhotos)
        }
    }

}
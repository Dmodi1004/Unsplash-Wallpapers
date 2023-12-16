package com.example.unsplashwallpapers.Models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorite_photos"
)
data class FavoritePhotos(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val description: String,
    val imageUrl: String,
    val userName: String,
    val userProfile: String
)

package com.example.unsplashwallpapers.Models

import java.io.Serializable

data class Collection(
    val id: String? = "",
    val title: String? = "",
    val description: String? = "",
    val total_photos: Int? = null,
    val cover_photo: Photo? = null,
    val user: User? = null
): Serializable

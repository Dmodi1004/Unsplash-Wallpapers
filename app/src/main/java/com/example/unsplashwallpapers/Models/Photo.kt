package com.example.unsplashwallpapers.Models

import java.io.Serializable

data class Photo(
    val id: String? = "",
    val description: String? = "",
    val urls: PhotoUrl? = null,
    val user: User? = null
) : Serializable
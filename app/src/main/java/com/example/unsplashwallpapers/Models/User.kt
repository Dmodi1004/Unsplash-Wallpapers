package com.example.unsplashwallpapers.Models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
    val id: String? = "",
    val username: String? = "",
    val profile_image: ProfileImage? = null
): Serializable

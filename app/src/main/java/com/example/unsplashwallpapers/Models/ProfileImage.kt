package com.example.unsplashwallpapers.Models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProfileImage(
    val small: String? = "",
    val medium: String? = "",
    val large: String? = ""
): Serializable
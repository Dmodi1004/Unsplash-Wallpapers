package com.example.unsplashwallpapers.Models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PhotoUrl(
    val full: String? = "",
    val regular: String? = ""
): Serializable
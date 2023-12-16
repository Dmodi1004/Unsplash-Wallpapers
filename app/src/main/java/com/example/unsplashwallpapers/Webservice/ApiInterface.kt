package com.example.unsplashwallpapers.Webservice

import com.example.unsplashwallpapers.Models.Collection
import com.example.unsplashwallpapers.Models.Photo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("photos")
    fun getPhotos(): Call<List<Photo>>

    @GET("collections") // OR collections/featured
    fun getCollections(): Call<List<Collection>>

    @GET("collections/{id}")
    fun getInformationOfCollection(@Path("id") id: String): Call<Collection> // id is String or Int

    @GET("collections/{id}/photos")
    fun getPhotosOfCollection(@Path("id") id: String): Call<List<Photo>>

    @GET("photos/{id}")
    fun getPhoto(@Path("id") id: String): Call<Photo>

}
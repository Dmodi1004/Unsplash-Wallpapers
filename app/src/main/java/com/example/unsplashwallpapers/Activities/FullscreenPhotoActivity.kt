package com.example.unsplashwallpapers.Activities

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.unsplashwallpapers.Models.FavoritePhotos
import com.example.unsplashwallpapers.Models.FavoriteViewModel
import com.example.unsplashwallpapers.Models.Photo
import com.example.unsplashwallpapers.R
import com.example.unsplashwallpapers.Utils.Functions
import com.example.unsplashwallpapers.Webservice.ApiInterface
import com.example.unsplashwallpapers.Webservice.ServiceGenerator
import com.example.unsplashwallpapers.databinding.ActivityFullscreenPhotoBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FullscreenPhotoActivity : AppCompatActivity() {

    private var TAG = FullscreenPhotoActivity::class.simpleName

    private lateinit var photoBitmap: Bitmap

    private lateinit var favViewModel: FavoriteViewModel

    //    private val photos: MutableList<Photo> = mutableListOf()
    private lateinit var photos: Photo

    private val binding by lazy {
        ActivityFullscreenPhotoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        val photoId = intent.getStringExtra("photoId")

        favViewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]
        photos = Photo()

        binding.apply {

            activityFullscreenPhotoFabFavorite.setOnClickListener {
                setFabFavorite()
            }

            activityFullscreenPhotoFabWallpaper.setOnClickListener {
                setFabWallPaper()
            }

            getPhoto(photoId.toString())

        }

    }

    private fun getPhoto(id: String) {
        val apiInterface = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<Photo> = apiInterface!!.getPhoto(id)
        call.enqueue(object : Callback<Photo> {
            override fun onResponse(call: Call<Photo>, response: Response<Photo>) {

                if (response.isSuccessful) {
                    val photo: Photo = response.body()!!
                    updateUI(photo)
                }

            }

            override fun onFailure(call: Call<Photo>, t: Throwable) {

            }

        })
    }

    private fun updateUI(photo: Photo) {

        binding.apply {

            try {
                activityFullscreenPhotoUserName.text = photo.user!!.username

                Glide.with(applicationContext)
                    .load(photo.user.profile_image!!.large)
                    .into(activityFullscreenPhotoUserAvatar)

                Glide.with(applicationContext)
                    .asBitmap()
                    .load(photo.urls!!.regular)
                    .into(object : SimpleTarget<Bitmap>() {
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            activityFullscreenPhotoPhoto.setImageBitmap(resource)
                            photoBitmap = resource
                        }

                    })

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun setFabWallPaper() {

        if (photoBitmap != null) {

            if (Functions.setWallpaper(this, photoBitmap)) {
                Toast.makeText(this, "Set Wallpaper Successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Set Wallpaper Failed", Toast.LENGTH_SHORT).show()
            }

            binding.activityFullscreenPhotoFabMenu.close(true)
        }
    }

    private fun setFabFavorite() {

        val description = photos.description ?: ""
        val imageUrl = photos.urls?.regular ?: ""
        val username = photos.user?.username ?: ""
        val userprofile = photos.user?.profile_image?.large ?: ""

        val favoritePhotos = FavoritePhotos(
            id = 0L,
            description = description,
            imageUrl = imageUrl,
            userName = username,
            userProfile = userprofile
        )
        favViewModel.savePhoto(favoritePhotos)
        Toast.makeText(this, "Successfully added", Toast.LENGTH_SHORT).show()

        binding.activityFullscreenPhotoFabFavorite.setImageDrawable(
            ContextCompat.getDrawable(this, R.drawable.ic_favorite_yellow)
        )


        binding.activityFullscreenPhotoFabMenu.close(true)
    }

}
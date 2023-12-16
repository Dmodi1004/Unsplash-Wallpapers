package com.example.unsplashwallpapers.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.unsplashwallpapers.Fragments.CollectionsFragment
import com.example.unsplashwallpapers.Fragments.FavoriteFragment
import com.example.unsplashwallpapers.Fragments.PhotosFragment
import com.example.unsplashwallpapers.R
import com.example.unsplashwallpapers.Utils.Functions.Companion.changeMainFunctions
import com.example.unsplashwallpapers.Utils.Functions.Companion.showToast
import com.example.unsplashwallpapers.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val photosFragment = PhotosFragment()
        changeMainFunctions(this@MainActivity, photosFragment)

        binding.apply {
            toolBar.setNavigationOnClickListener {
                drawerLayout.open()
            }

            navigationView.setNavigationItemSelectedListener {
                drawerLayout.close()
                it.isCheckable = true

                when (it.itemId) {
                    R.id.nav_photos -> {
                        val photosFragment = PhotosFragment()
                        changeMainFunctions(this@MainActivity, photosFragment)

                        showToast(this@MainActivity, "Photos")
                    }

                    R.id.nav_collections -> {

                        val collectionFragment = CollectionsFragment()
                        changeMainFunctions(this@MainActivity, collectionFragment)

                        showToast(this@MainActivity, "Collections")

                    }

                    R.id.nav_favorite -> {

                        val favoriteFragment = FavoriteFragment()
                        changeMainFunctions(this@MainActivity, favoriteFragment)

                        showToast(this@MainActivity, "Favorite")
                    }
                }


                return@setNavigationItemSelectedListener true
            }

        }

    }
}
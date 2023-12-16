package com.example.unsplashwallpapers.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.unsplashwallpapers.Models.FavoritePhotos
import com.example.unsplashwallpapers.Models.Photo

@Database(
    entities = [FavoritePhotos::class],
    version = 2,
)
abstract class FavPhotosDatabase : RoomDatabase() {

    abstract fun photoDao(): PhotoDao

    companion object {
        @Volatile
        private var INSTANCE: FavPhotosDatabase? = null

        fun getDatabase(context: Context): FavPhotosDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavPhotosDatabase::class.java,
                    "favorite_photos",

                    )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }

    }

}
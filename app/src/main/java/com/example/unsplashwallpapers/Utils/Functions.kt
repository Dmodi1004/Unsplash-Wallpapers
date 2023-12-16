package com.example.unsplashwallpapers.Utils

import android.app.Activity
import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.RectF
import android.util.DisplayMetrics
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.unsplashwallpapers.R
import okio.IOException


class Functions {

    companion object {

        fun Activity.showToast(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        fun changeMainFunctions(fragmentActivity: FragmentActivity, fragment: Fragment) {
            fragmentActivity.supportFragmentManager
                .beginTransaction()
                .replace(R.id.mainContainer, fragment)
                .commit()
        }

        fun changeFragmentWithBack(fragmentActivity: FragmentActivity, fragment: Fragment) {
            fragmentActivity.supportFragmentManager
                .beginTransaction()
                .replace(R.id.mainContainer, fragment)
                .addToBackStack(null)
                .commit()
        }

        fun setWallpaper(activity: Activity, bitmap: Bitmap): Boolean {
            val displayMetrics = DisplayMetrics()
            activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
            val height: Int = displayMetrics.heightPixels
            val width: Int = displayMetrics.widthPixels
            val tempBitmap: Bitmap? = scaleCenterCrop(bitmap, height, width)

            val wallpaperManager: WallpaperManager = WallpaperManager.getInstance(activity)
            try {
                wallpaperManager.setBitmap(tempBitmap)
                return true
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return false
        }

        fun scaleCenterCrop(source: Bitmap, newHeight: Int, newWidth: Int): Bitmap? {
            val sourceWidth = source.width
            val sourceHeight = source.height

            // Compute the scaling factors to fit the new height and width, respectively.
            // To cover the final image, the final scaling will be the bigger
            // of these two.
            val xScale = newWidth.toFloat() / sourceWidth
            val yScale = newHeight.toFloat() / sourceHeight
            val scale = Math.max(xScale, yScale)

            // Now get the size of the source bitmap when scaled
            val scaledWidth = scale * sourceWidth
            val scaledHeight = scale * sourceHeight

            // Let's find out the upper left coordinates if the scaled bitmap
            // should be centered in the new size give by the parameters
            val left = (newWidth - scaledWidth) / 2
            val top = (newHeight - scaledHeight) / 2

            // The target rectangle for the new, scaled version of the source bitmap will now
            // be
            val targetRect = RectF(left, top, left + scaledWidth, top + scaledHeight)

            // Finally, we create a new bitmap of the specified size and draw our new,
            // scaled bitmap onto it.
            val dest = Bitmap.createBitmap(newWidth, newHeight, source.config)
            val canvas = Canvas(dest)
            canvas.drawBitmap(source, null, targetRect, null)
            return dest
        }

    }

}
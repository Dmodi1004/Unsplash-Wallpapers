package com.example.unsplashwallpapers.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.unsplashwallpapers.Activities.FullscreenPhotoActivity
import com.example.unsplashwallpapers.Models.Photo
import com.example.unsplashwallpapers.R
import com.example.unsplashwallpapers.databinding.ItemPhotosBinding

class PhotosAdapter(private val context: Context, private val photos: List<Photo>) :
    RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemPhotosBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: Photo, context: Context) {
            binding.apply {
                model.apply {
                    itemUserName.text = user?.username

                    Glide.with(context)
                        .load(user?.profile_image?.large)
                        .placeholder(R.drawable.user_avatar)
                        .into(itemUserAvatar)

                    Glide.with(context)
                        .load(urls?.regular)
                        .placeholder(R.drawable.image_avatar)
                        .into(itemPhoto)

                    itemPhotoLayout.setOnClickListener {
                        val position = adapterPosition
                        val photoId = model.id
                        val intent = Intent(context, FullscreenPhotoActivity::class.java)
                        intent.putExtra("photoId", photoId)
                        context.startActivity(intent)
                    }

                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemPhotosBinding.inflate(LayoutInflater.from(context), parent, false)
        )
    }

    override fun getItemCount(): Int = photos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(
            model = photos[position], context = context
        )
    }

}
package com.example.unsplashwallpapers.Adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.unsplashwallpapers.CollectionClickListener
import com.example.unsplashwallpapers.Fragments.CollectionFragment
import com.example.unsplashwallpapers.Models.Collection
import com.example.unsplashwallpapers.R
import com.example.unsplashwallpapers.Utils.Functions
import com.example.unsplashwallpapers.databinding.ItemCollectionsBinding

class CollectionsAdapter(
    val context: Context,
    val collections: List<Collection>,
    val clickListener: CollectionClickListener
) : RecyclerView.Adapter<CollectionsAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemCollectionsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: Collection, context: Context) {

            binding.apply {
                model.apply {
                    if (title != null) {
                        itemCollectionTitle.text = model.title
                    }
                    itemCollectionTotalPhotos.text = "$total_photos Photos"

                    Glide.with(context)
                        .load(cover_photo?.urls?.regular)
                        .placeholder(R.drawable.image_avatar)
                        .into(itemCollectionPhoto)

                    mainLayout.setOnClickListener {
                        Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show()
                    }

                }
            }


        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ItemCollectionsBinding.inflate(LayoutInflater.from(context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(model = collections[position], context = context)

        holder.itemView.setOnClickListener {
            clickListener.onCollectionClick(position)
        }
    }

    override fun getItemCount(): Int = collections.size

}
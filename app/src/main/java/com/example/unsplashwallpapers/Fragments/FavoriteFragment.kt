package com.example.unsplashwallpapers.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.unsplashwallpapers.R
import com.example.unsplashwallpapers.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {

    private val binding by lazy {
        FragmentFavoriteBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
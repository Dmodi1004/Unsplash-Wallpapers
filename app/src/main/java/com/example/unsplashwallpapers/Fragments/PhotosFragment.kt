package com.example.unsplashwallpapers.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.unsplashwallpapers.Adapters.PhotosAdapter
import com.example.unsplashwallpapers.Models.Photo
import com.example.unsplashwallpapers.Webservice.ApiInterface
import com.example.unsplashwallpapers.Webservice.ServiceGenerator
import com.example.unsplashwallpapers.databinding.FragmentPhotosBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PhotosFragment : Fragment() {

    private val TAG = PhotosFragment::class.simpleName
    private val photos: MutableList<Photo> = mutableListOf()
    private lateinit var photosAdapter: PhotosAdapter

    private val binding by lazy {
        FragmentPhotosBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.apply {

            photosAdapter = PhotosAdapter(requireContext(), photos)

            val layoutManager = LinearLayoutManager(context)
            fragmentPhotosRv.layoutManager = layoutManager
            fragmentPhotosRv.adapter = photosAdapter

            showProgressBar(true)

            getPhotos()
        }

        return binding.root
    }

    private fun getPhotos() {
        val apiInterface = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<List<Photo>> = apiInterface!!.getPhotos()
        call.enqueue(   object : Callback<List<Photo>> {
            override fun onResponse(call: Call<List<Photo>>, response: Response<List<Photo>>) {
                photos.clear()
                if (response.isSuccessful) {
                    photos.addAll(response.body()!!)
                    photosAdapter.notifyDataSetChanged()
                } else {
                    Log.e(TAG, "onResponse: ${response.message()}")
                }
                showProgressBar(false)
            }
            override fun onFailure(call: Call<List<Photo>>, t: Throwable) {
                Log.e(TAG, "onResponse: ${t.message}")
                showProgressBar(false)
            }

        })

    }

    private fun showProgressBar(isShow: Boolean) {
        if (isShow) {
            binding.fragmentPhotosProgressBar.visibility = View.VISIBLE
            binding.fragmentPhotosRv.visibility = View.GONE
        } else {
            binding.fragmentPhotosProgressBar.visibility = View.GONE
            binding.fragmentPhotosRv.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
package com.example.unsplashwallpapers.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.bumptech.glide.Glide
import com.example.unsplashwallpapers.Adapters.CollectionsAdapter
import com.example.unsplashwallpapers.Adapters.PhotosAdapter
import com.example.unsplashwallpapers.Models.Collection
import com.example.unsplashwallpapers.Models.Photo
import com.example.unsplashwallpapers.R
import com.example.unsplashwallpapers.Webservice.ApiInterface
import com.example.unsplashwallpapers.Webservice.ServiceGenerator
import com.example.unsplashwallpapers.databinding.FragmentCollectionBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CollectionFragment : Fragment() {

    private val TAG = CollectionFragment::class.simpleName
    private val photos: MutableList<Photo> = mutableListOf()
    private lateinit var photosAdapter: PhotosAdapter

    private val binding by lazy {
        FragmentCollectionBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.apply {

            photosAdapter = PhotosAdapter(requireContext(), photos)

            val layManager = LinearLayoutManager(activity)
            fragmentCollectionRv.layoutManager = layManager
            fragmentCollectionRv.adapter = photosAdapter

            val bundle = arguments
            val collectionId = bundle!!.getString("collectionId")

            showProgressBar(true)

            getInformationOfCollection(collectionId.toString())
            getPhotosOfCollection(collectionId.toString())

        }

        return binding.root
    }

    private fun getInformationOfCollection(collectionId: String) {
        val apiInterface = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<Collection> = apiInterface!!.getInformationOfCollection(collectionId)
        call.enqueue(object : Callback<Collection> {
            override fun onResponse(call: Call<Collection>, response: Response<Collection>) {

                if (response.isSuccessful) {
                    val collection: Collection? = response.body()

                    binding.apply {
                        fragmentCollectionTitle.text = collection!!.title
                        fragmentCollectionDescription.text = collection.description
                        fragmentCollectionUsername.text = collection.user!!.username

                        Glide.with(requireActivity())
                            .load(collection.user.profile_image!!.large)
                            .placeholder(R.drawable.user_avatar)
                            .into(fragmentCollectionUserAvatar)
                    }
                } else {
                    Log.e(TAG, "Fail: ${response.message()}")
                }

            }

            override fun onFailure(call: Call<Collection>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    private fun getPhotosOfCollection(collectionId: String) {

        val apiInterface = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<List<Photo>> = apiInterface!!.getPhotosOfCollection(collectionId)
        call.enqueue(object : Callback<List<Photo>> {
            override fun onResponse(call: Call<List<Photo>>, response: Response<List<Photo>>) {

                photos.clear()
                if (response.isSuccessful) {
                    photos.addAll(response.body()!!)
                photosAdapter.notifyDataSetChanged()
                } else {
                    Log.e(TAG, "Fail: ${response.message()}")
                }
                showProgressBar(false)
            }

            override fun onFailure(call: Call<List<Photo>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
                showProgressBar(false)
            }

        })

    }

    private fun showProgressBar(isShow: Boolean) {
        if (isShow) {
            binding.fragmentCollectionProgressBar.visibility = View.VISIBLE
            binding.fragmentCollectionRv.visibility = View.GONE
        } else {
            binding.fragmentCollectionProgressBar.visibility = View.GONE
            binding.fragmentCollectionRv.visibility = View.VISIBLE
        }
    }

}
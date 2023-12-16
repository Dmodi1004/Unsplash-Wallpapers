package com.example.unsplashwallpapers.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.unsplashwallpapers.Adapters.CollectionsAdapter
import com.example.unsplashwallpapers.CollectionClickListener
import com.example.unsplashwallpapers.Models.Collection
import com.example.unsplashwallpapers.Utils.Functions
import com.example.unsplashwallpapers.Webservice.ApiInterface
import com.example.unsplashwallpapers.Webservice.ServiceGenerator
import com.example.unsplashwallpapers.databinding.FragmentCollectionsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CollectionsFragment : Fragment(), CollectionClickListener {

    private val TAG = CollectionsFragment::class.simpleName
    private val collections: MutableList<Collection> = mutableListOf()
    private lateinit var collectionsAdapter: CollectionsAdapter

    private val binding by lazy {
        FragmentCollectionsBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.apply {

            collectionsAdapter = CollectionsAdapter(requireContext(), collections, this@CollectionsFragment)

            val layoutManager = GridLayoutManager(context, 2)
            fragmentCollectionsRv.layoutManager = layoutManager
            fragmentCollectionsRv.adapter = collectionsAdapter

            showProgressBar(true)

            getCollections()

        }
        return binding.root
    }

    fun setRecyclerView(position: Int) {
        val collection = collections[position]
        val bundle = Bundle()
        bundle.putString("collectionId", collection.id)
        val collectionFragment = CollectionFragment()
        collectionFragment.arguments = bundle
        Functions.changeFragmentWithBack(requireActivity(), collectionFragment)
        collectionsAdapter.notifyDataSetChanged()
    }

    private fun getCollections() {

        val apiInterface = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<List<Collection>> = apiInterface!!.getCollections()
        call.enqueue(object : Callback<List<Collection>> {
            override fun onResponse(
                call: Call<List<Collection>>,
                response: Response<List<Collection>>
            ) {
                collections.clear()
                if (response.isSuccessful) {
                    collections.addAll(response.body()!!)
                    collectionsAdapter.notifyDataSetChanged()
                } else {
                    Log.e(TAG, "Failed: ${response.message()}")
                }
                showProgressBar(false)

            }

            override fun onFailure(call: Call<List<Collection>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
                showProgressBar(false)
            }

        })
    }

    private fun showProgressBar(isShow: Boolean) {
        if (isShow) {
            binding.fragmentCollectionsProgressBar.visibility = View.VISIBLE
            binding.fragmentCollectionsRv.visibility = View.GONE
        } else {
            binding.fragmentCollectionsProgressBar.visibility = View.GONE
            binding.fragmentCollectionsRv.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onCollectionClick(position: Int) {
        setRecyclerView(position)
    }

}
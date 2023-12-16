package com.example.unsplashwallpapers.Webservice

import com.example.unsplashwallpapers.Utils.Constants
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class ServiceGenerator {

    companion object {

    private var retrofit: Retrofit? = null
    private val gson = GsonBuilder().create()

    private val httpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val okHttpClientBuilder = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Client-ID " + Constants.APPLICATION_ID)
                    .build()

                return chain.proceed(request)
            }

        })

    private val okHttpClient = okHttpClientBuilder.build()

        fun <T> createService(serviceClass: Class<T>): T? {

            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(Constants.BASE_API_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }
            return retrofit?.create(serviceClass)
        }
    }


}
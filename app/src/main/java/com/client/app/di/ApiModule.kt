package com.client.app.di

import android.util.Log
import com.client.app.data.api.VideoApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


val BASE_URL="http://125.234.170.249/"
val okHttpClient:OkHttpClient by lazy {
    OkHttpClient.Builder().addInterceptor { chain ->
        val originalRequest = chain.request()

        val url = originalRequest.url().toString()
        Log.e("ApiModule", "URL: $url")

        val modifiedRequest = originalRequest.newBuilder()
            .header("Accept-Language", "en-US")
            .build()
        chain.proceed(modifiedRequest)
    }.build()
}

val retrofit:Retrofit by  lazy {
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

val videoApi: VideoApi by lazy {
    retrofit.create(VideoApi::class.java)
}


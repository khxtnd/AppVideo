package com.client.app.data

import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiUtilities{
    val BASE_URL="http://125.234.170.249/"
    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor { chain ->
            val originalRequest = chain.request()

            val url = originalRequest.url().toString()
            Log.e("ApiUtilities", "URL: $url")

            val modifiedRequest = originalRequest.newBuilder()
                .header("Accept-Language", "en-US")
                .build()
            chain.proceed(modifiedRequest)
        }.build()
    }

    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

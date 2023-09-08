package com.client.app.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyRetrofitClient {
    companion object {
        private fun createOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder().addInterceptor { chain ->
                val originalRequest = chain.request()
                val modifiedRequest = originalRequest.newBuilder()
                    .header("Accept-Language", "en-US")
                    .build()
                chain.proceed(modifiedRequest)
            }.build()
        }

        fun createRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl("http://125.234.170.249/")
                .client(createOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}
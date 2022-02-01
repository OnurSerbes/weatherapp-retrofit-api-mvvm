package com.example.weatherapinew.api

import com.example.weatherapinew.utils.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {

    private val retrofit by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // we will use Gson in here to convert json to kotlin class
            .build()
    }

    val api: WeatherAPI by lazy{
        retrofit.create(WeatherAPI::class.java)
    }
}
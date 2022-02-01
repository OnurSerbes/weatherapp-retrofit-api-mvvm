package com.example.weatherapinew.repository

import com.example.weatherapinew.api.RetrofitInstance
import com.example.weatherapinew.model.Weather
import retrofit2.Response

class Repository {

    suspend fun getWeather(): Response<Weather>{
        return RetrofitInstance.api.getWeather()
    }

    suspend fun getWeatherCustom(cityName: String, apiKey: String): Response<Weather>{
        return RetrofitInstance.api.getWeatherCustom(cityName, apiKey)
    }
}
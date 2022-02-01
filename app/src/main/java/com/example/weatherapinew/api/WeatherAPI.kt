package com.example.weatherapinew.api

import com.example.weatherapinew.model.Weather
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherAPI {

    @GET("data/2.5/weather?q=ankara&appid={API Key}")
    suspend fun getWeather(): Response<Weather>

    @GET("data/2.5/weather?&units=metric")
    suspend fun getWeatherCustom(
        @Query("q") cityName: String,
        @Query("apiKey") apiKey: String
    ): Response<Weather>

}
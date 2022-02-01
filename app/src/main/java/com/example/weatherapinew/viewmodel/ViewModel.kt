package com.example.weatherapinew.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.weatherapinew.model.Weather
import com.example.weatherapinew.repository.Repository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response

class ViewModel(private val repository: Repository): ViewModel() {

    val myResponse: MutableLiveData<Response<Weather>> = MutableLiveData()
    val myCustomResponse: MutableLiveData<Response<Weather>> = MutableLiveData()


    fun getWeather(){
        viewModelScope.launch {
            val response = repository.getWeather()
            myResponse.value = response
        }
    }

    fun getWeatherCustom(cityName: String, apiKey: String){
        viewModelScope.launch {
            val response = repository.getWeatherCustom(cityName, apiKey)
            myCustomResponse.value = response
        }
    }

}
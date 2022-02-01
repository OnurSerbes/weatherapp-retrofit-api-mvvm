package com.example.weatherapinew

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.util.Log
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.weatherapinew.model.Weather
import com.example.weatherapinew.model.WeatherX
import com.example.weatherapinew.repository.Repository
import com.example.weatherapinew.viewmodel.ViewModel
import com.example.weatherapinew.viewmodel.ViewModelFactory
import retrofit2.http.GET
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    companion object {
        const val apiKey: String = "API Key"
    }

    private lateinit var viewModel: ViewModel

    var textView_city: TextView ?= null
    var textView_country: TextView ?= null
    var textView_degree: TextView ?= null
    var textView_detail: TextView ?= null
    var textView_min: TextView ?= null
    var textView_max: TextView ?= null
    var image_weather: ImageView? = null
    var editText: EditText? =null

    var cityName: String ?= null
    var button: Button ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView_city = findViewById(R.id.textView_city)
        textView_country = findViewById(R.id.textView_country)
        textView_degree = findViewById(R.id.textView_degree)
        textView_detail = findViewById(R.id.textView_detail)
        textView_min = findViewById(R.id.textView_min)
        textView_max = findViewById(R.id.textView_max)
//        image_weather = findViewById(R.id.image_weather)
        button = findViewById(R.id.btn_search)
        editText = findViewById(R.id.cityNameEditText)

        getDataFromUser()

        //RETROFIT SETUP
        val repository = Repository()
        val viewModelFactory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this,viewModelFactory)[ViewModel::class.java] // initialize the viewModel // we will pass repository to the viewModel

    }

    private fun getDataFromUser() {
        try {
            if(editText?.text.toString() != null){
                button?.setOnClickListener {
                    Log.d(TAG, "getFromEditText: button clicked")
                    cityName = editText?.text.toString()
                    //it will add to the data to getDataCustom()
                    cityName?.let { getDataCustom(it, apiKey) }
                    Log.d(TAG, "getFromEditText: values getting set")
                }
                Toast.makeText(this,"Please fill the city name", Toast.LENGTH_SHORT).show()
            }
        }catch (e: Exception){
            println("the exception is: $e")
        }
    }

    private fun getDataCustom(cityName: String, apiKey: String) {
        viewModel.getWeatherCustom(cityName,apiKey)
        viewModel.myCustomResponse.observe(this,Observer{ response ->
            if (response.isSuccessful){
                Log.d("Response", response.body().toString())
                response.body()?.let {
                    textView_city?.text = response.body()!!.name
                    textView_degree?.text = "${response.body()!!.main.temp.toString()}°C"
                    textView_detail?.text = "Weather detail: ${response.body()!!.weather[0].description.toString()}"
                    textView_min?.text = "min ${response.body()!!.main.temp_min.toString()}°C"
                    textView_max?.text = "max ${response.body()!!.main.temp_max.toString()}°C"
                    textView_country?.text =response.body()!!.sys.country.toString()

//                    image_weather?.let { it1 ->
//                        Glide.with(this)
//                            .load("http://openweathermap.org/img/wn/${response.body()!!.weather[0].icon}@2x.png")
//                            .into(it1)
//                    }
                }
            }else{
                Toast.makeText(this,response.code(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    //this is the hardcoded version of getting data
    private fun getData() {
        viewModel.getWeather()
        viewModel.myResponse.observe(this,Observer{response ->
            if(response.isSuccessful){
                Log.d("Response", response.body().toString())
                response.body()?.let {
                    textView_city?.text = response.body()!!.name
                    textView_degree?.text = response.body()!!.main.temp.toString()
                    textView_detail?.text = response.body()!!.weather[0].toString()

                    image_weather?.let { it ->
                        Glide.with(this)
                            .load("http://openweathermap.org/img/wn" + response.body()!!.weather[0].icon + "@2x.png")
                            .into(it)
                    }
                }

            }else{
                Toast.makeText(this,response.code(), Toast.LENGTH_SHORT).show()
            }
        })
    }
}



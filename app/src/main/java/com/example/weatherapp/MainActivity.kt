package com.example.weatherapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val API_KEY = "4afdb35852243b3ee0985c28bece9c86"
    private val BASE_URL = "https://api.openweathermap.org/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cityInput = findViewById<EditText>(R.id.etCityName)
        val searchButton = findViewById<Button>(R.id.btnSearch)
        val cityText = findViewById<TextView>(R.id.tvCity)
        val tempText = findViewById<TextView>(R.id.tvTemp)
        val conditionText = findViewById<TextView>(R.id.tvCondition)
        val humidityText = findViewById<TextView>(R.id.tvHumidity)
        val windText = findViewById<TextView>(R.id.tvWind)

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(WeatherApi::class.java)

        searchButton.setOnClickListener {
            val city = cityInput.text.toString().trim()

            if (city.isEmpty()) {
                Toast.makeText(this, "Please enter a city name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            api.getWeather(city, API_KEY).enqueue(object : Callback<WeatherResponse> {
                override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                    if (response.isSuccessful && response.body() != null) {
                        val data = response.body()!!
                        cityText.text = data.name
                        tempText.text = "${data.main.temp}°C"
                        conditionText.text = data.weather[0].description
                        humidityText.text = "Humidity: ${data.main.humidity}%"
                        windText.text = "Wind Speed: ${data.wind.speed} km/h"
                    } else {
                        Toast.makeText(this@MainActivity, "City not found", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Network error, check your connection", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}

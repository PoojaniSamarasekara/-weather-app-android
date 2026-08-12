package com.example.weatherapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val API_KEY = "YOUR_API_KEY" // TODO: Replace with real API Key

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etCityName = findViewById<EditText>(R.id.etCityName)
        val btnSearch = findViewById<Button>(R.id.btnSearch)
        val tvStatus = findViewById<TextView>(R.id.tvStatus)
        val tvCity = findViewById<TextView>(R.id.tvCity)
        val tvTemp = findViewById<TextView>(R.id.tvTemp)
        val tvCondition = findViewById<TextView>(R.id.tvCondition)
        val tvHumidity = findViewById<TextView>(R.id.tvHumidity)
        val tvWind = findViewById<TextView>(R.id.tvWind)

        btnSearch.setOnClickListener {
            val city = etCityName.text.toString().trim()
            if (city.isEmpty()) {
                tvStatus.text = "Please enter a city name"
                tvStatus.visibility = View.VISIBLE
                return@setOnClickListener
            }

            RetrofitClient.service.getWeather(city, API_KEY)
                .enqueue(object : Callback<WeatherResponse> {
                    override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                        if (response.isSuccessful && response.body() != null) {
                            val data = response.body()!!

                            tvCity.text = data.name
                            tvTemp.text = "${data.main.temp}°C"
                            tvCondition.text = data.weather.firstOrNull()?.description ?: "N/A"
                            tvHumidity.text = "Humidity: ${data.main.humidity}%"
                            tvWind.text = "Wind Speed: ${data.wind.speed} km/h"
                            tvStatus.text = ""
                            tvStatus.visibility = View.GONE
                        } else {
                            val errorMsg = when (response.code()) {
                                404 -> "City not found. Please check the name."
                                401 -> "Invalid API Key."
                                else -> "Error: ${response.code()}"
                            }
                            tvStatus.text = errorMsg
                            tvStatus.visibility = View.VISIBLE

                            // Clear previous data
                            tvCity.text = ""
                            tvTemp.text = ""
                            tvCondition.text = ""
                            tvHumidity.text = ""
                            tvWind.text = ""
                        }
                    }

                    override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                        tvStatus.text = "Network Failure: ${t.message}"
                        tvStatus.visibility = View.VISIBLE

                        // Clear previous data
                        tvCity.text = ""
                        tvTemp.text = ""
                        tvCondition.text = ""
                        tvHumidity.text = ""
                        tvWind.text = ""
                    }
                })
        }
    }
}

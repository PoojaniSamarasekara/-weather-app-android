package com.example.weatherapp

import android.os.Bundle
import android.util.Log
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

    private val API_KEY = "4afdb35852243b3ee0985c28bece9c86"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
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

            // Task 08: Empty city validation
            if (city.isEmpty()) {
                clearResults(
                    tvCity,
                    tvTemp,
                    tvCondition,
                    tvHumidity,
                    tvWind
                )

                tvStatus.text = "Please enter a city name"
                tvStatus.visibility = View.VISIBLE

                return@setOnClickListener
            }

            RetrofitClient.service.getWeather(city, API_KEY)
                .enqueue(object : Callback<WeatherResponse> {

                    // Task 07 + Task 08
                    override fun onResponse(
                        call: Call<WeatherResponse>,
                        response: Response<WeatherResponse>
                    ) {

                        if (response.isSuccessful && response.body() != null) {

                            // Task 07: Get API data
                            val data = response.body()!!

                            tvCity.text = data.name
                            tvTemp.text = "${data.main.temp}°C"
                            tvCondition.text =
                                data.weather.firstOrNull()?.description ?: "N/A"
                            tvHumidity.text =
                                "Humidity: ${data.main.humidity}%"
                            tvWind.text =
                                "Wind Speed: ${data.wind.speed} km/h"

                            tvStatus.text = ""
                            tvStatus.visibility = View.GONE

                        } else {

                            // Task 08: Clear old results
                            clearResults(
                                tvCity,
                                tvTemp,
                                tvCondition,
                                tvHumidity,
                                tvWind
                            )

                            // Invalid city
                            if (response.code() == 404) {

                                tvStatus.text =
                                    "City not found. Please check the spelling."

                            } else {

                                // Generic API error
                                tvStatus.text =
                                    "Something went wrong. Please try again later."

                                Log.e(
                                    "WeatherAPI",
                                    "API error: ${response.code()}"
                                )
                            }

                            tvStatus.visibility = View.VISIBLE
                        }
                    }

                    // Task 08: Network error
                    override fun onFailure(
                        call: Call<WeatherResponse>,
                        t: Throwable
                    ) {

                        clearResults(
                            tvCity,
                            tvTemp,
                            tvCondition,
                            tvHumidity,
                            tvWind
                        )

                        tvStatus.text =
                            "Network error. Check your connection and try again."

                        tvStatus.visibility = View.VISIBLE

                        Log.e(
                            "WeatherAPI",
                            "Network error",
                            t
                        )
                    }
                })
        }
    }

    // Task 08: Clear previous weather results
    private fun clearResults(
        tvCity: TextView,
        tvTemp: TextView,
        tvCondition: TextView,
        tvHumidity: TextView,
        tvWind: TextView
    ) {
        tvCity.text = ""
        tvTemp.text = ""
        tvCondition.text = ""
        tvHumidity.text = ""
        tvWind.text = ""
    }
}
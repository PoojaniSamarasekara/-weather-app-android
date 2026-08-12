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
                        if (response.isSuccessful) {
                            val weather = response.body()
                            // TODO: hand off to Member 4's display logic
                            tvStatus.visibility = View.GONE
                        } else {
                            // TODO: hand off to Member 4's error logic
                            tvStatus.text = "Error: ${response.code()}"
                            tvStatus.visibility = View.VISIBLE
                        }
                    }

                    override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                        // network failure — hand off to Member 4's error handling
                        tvStatus.text = "Network Failure: ${t.message}"
                        tvStatus.visibility = View.VISIBLE
                    }
                })
        }
    }
}

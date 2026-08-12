package com.example.weatherapp

data class WeatherResponse(
    val name: String, // city name
    val main: MainInfo,
    val weather: List<WeatherDesc>,
    val wind: Wind
)

data class MainInfo(val temp: Double, val humidity: Int)

data class WeatherDesc(val description: String)

data class Wind(val speed: Double)

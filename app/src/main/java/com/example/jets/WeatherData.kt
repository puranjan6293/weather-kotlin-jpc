package com.example.jets

data class WeatherResponse(
    val name: String,
    val main: Main,
    val weather: List<Weather>
)

data class Main(
    val temp: Double,
    val pressure: Int
)

data class Weather(
    val description: String
)

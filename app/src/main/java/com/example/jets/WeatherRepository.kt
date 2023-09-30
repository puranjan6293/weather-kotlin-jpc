package com.example.jets

// WeatherRepository.kt
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherRepository(private val apiKey: String) {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(WeatherApiService::class.java)

    suspend fun fetchWeather(cityName: String): WeatherResponse? {
        return try {
            service.getWeatherData(cityName, apiKey)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

package com.example.jets

// WeatherApiService.kt
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("weather")
    suspend fun getWeatherData(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String
    ): WeatherResponse
}

package com.example.infolangit.data

import com.example.infolangit.network.WeatherService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherRepository {
    private val weatherService: WeatherService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        weatherService = retrofit.create(WeatherService::class.java)
    }

    suspend fun getWeather(
        city: String,
        apiKey: String,
        units: String = "metric"
    ): WeatherResponse {
        return weatherService.getWeather(city, apiKey, units)
    }
}

// Update WeatherResponse to include additional fields
data class WeatherResponse(
    val main: Main,
    val weather: List<Weather>,
    val name: String,
    val wind: Wind,
    val sys: Sys
)

data class Main(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val humidity: Int
)

data class Weather(
    val main: String,
    val description: String,
    val icon: String
)

data class Wind(
    val speed: Double,
    val deg: Int
)

data class Sys(
    val country: String,
    val sunrise: Long,
    val sunset: Long
)
package com.example.infolangit

import retrofit2.http.GET
import retrofit2.http.Query

// Data Class untuk menyimpan data respons dari API
data class WeatherResponse(
    val main: Main,
    val weather: List<Weather>,
    val name: String
)

data class Main(
    val temp: Double // Suhu dalam Celsius
)

data class Weather(
    val main: String, // Kondisi cuaca (Clear, Rain, dll)
    val description: String // Deskripsi cuaca
)

// Interface Retrofit untuk OpenWeatherMap API
interface WeatherService {

    // Endpoint untuk mengambil data cuaca
    @GET("weather")
    suspend fun getWeather(
        @Query("q") city: String,            // Parameter: Nama Kota
        @Query("appid") apiKey: String,      // Parameter: API Key OpenWeatherMap
        @Query("units") units: String = "metric" // Satuan suhu (default: Celsius)
    ): WeatherResponse
}

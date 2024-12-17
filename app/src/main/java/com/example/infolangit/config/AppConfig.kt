package com.example.infolangit.config

object AppConfig {
    // OpenWeatherMap API Configuration
    const val OPENWEATHERMAP_BASE_URL = "https://api.openweathermap.org/data/2.5/"

    // Default city for weather fetch
    const val DEFAULT_CITY = "Manado"

    // Placeholder for API Key - in real app, use secure storage
    const val WEATHER_API_KEY = "53cef2ce01c406f9ddd5ac33e04f0f41"

    // Networking constants
    const val CONNECT_TIMEOUT = 30L
    const val READ_TIMEOUT = 30L
    const val WRITE_TIMEOUT = 30L
}

// Utility for handling sensitive information
object SecureConfig {
    // In a real app, implement secure key retrieval
    // This could involve encryption, keystore, or remote config
    fun getWeatherApiKey(): String = AppConfig.WEATHER_API_KEY
}
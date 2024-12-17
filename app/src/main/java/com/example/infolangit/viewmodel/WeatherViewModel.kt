package com.example.infolangit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.infolangit.data.WeatherRepository
import com.example.infolangit.data.WeatherResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val repository: WeatherRepository = WeatherRepository()
) : ViewModel() {
    private val _weatherState = MutableStateFlow<WeatherState>(WeatherState.Loading)
    val weatherState: StateFlow<WeatherState> = _weatherState.asStateFlow()

    fun fetchWeather(city: String, apiKey: String) {
        viewModelScope.launch {
            _weatherState.value = WeatherState.Loading
            try {
                val response = repository.getWeather(city, apiKey)
                _weatherState.value = WeatherState.Success(response)
            } catch (e: Exception) {
                _weatherState.value = WeatherState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }

    // Simplified callback method for backward compatibility
    fun fetchWeather(
        city: String,
        apiKey: String,
        onResult: (WeatherResponse?, String?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = repository.getWeather(city, apiKey)
                onResult(response, null)
            } catch (e: Exception) {
                onResult(null, e.message ?: "Unknown error occurred")
            }
        }
    }
}

// Sealed class to represent different states of weather data
sealed class WeatherState {
    object Loading : WeatherState()
    data class Success(val data: WeatherResponse) : WeatherState()
    data class Error(val message: String) : WeatherState()
}
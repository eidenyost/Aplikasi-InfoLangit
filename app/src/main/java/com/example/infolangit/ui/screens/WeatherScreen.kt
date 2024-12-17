package com.example.infolangit.ui.screens

import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.infolangit.R
import com.example.infolangit.data.WeatherResponse
import com.example.infolangit.ui.components.WeatherDetailItem
import com.example.infolangit.ui.theme.AppColors
import com.example.infolangit.viewmodel.WeatherViewModel
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = viewModel(),
    city: String = "Manado"
) {
    val apiKey = "53cef2ce01c406f9ddd5ac33e04f0f41" // Consider moving this to a secure config
    var weatherData by remember { mutableStateOf<WeatherResponse?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var refreshTrigger by remember { mutableStateOf(0) }

    // Fetch Weather Data
    LaunchedEffect(city, refreshTrigger) {
        isLoading = true
        viewModel.fetchWeather(city, apiKey) { response, error ->
            weatherData = response
            errorMessage = error
            isLoading = false

            // Logging
            response?.let {
                Log.d("WeatherScreen", "Weather data loaded: $it")
            } ?: run {
                Log.e("WeatherScreen", "Failed to load weather: $error")
            }
        }
    }

    // Main UI
    Scaffold(
        containerColor = AppColors.Background,
        contentColor = AppColors.TextPrimary
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            AppColors.Primary.copy(alpha = 0.1f),
                            AppColors.Background
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            when {
                isLoading -> LoadingIndicator()
                errorMessage != null -> ErrorDisplay(errorMessage!!) {
                    // Refresh functionality
                    refreshTrigger++
                }
                weatherData != null -> WeatherContent(weatherData!!) {
                    // Refresh functionality
                    refreshTrigger++
                }
                else -> NoDataDisplay()
            }
        }
    }
}

@Composable
fun LoadingIndicator() {
    // Pulsating animation for loading
    val infiniteTransition = rememberInfiniteTransition(label = "loading")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "scale"
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = AppColors.Primary,
            modifier = Modifier
                .size(70.dp)
                .scale(scale)
                .padding(16.dp),
            strokeWidth = 5.dp
        )
    }
}

@Composable
fun ErrorDisplay(
    message: String,
    onRefresh: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = AppColors.Error
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Error",
                style = MaterialTheme.typography.titleLarge,
                color = AppColors.Error,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Refresh Button
            FilledTonalButton(
                onClick = onRefresh,
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = AppColors.Primary.copy(alpha = 0.1f),
                    contentColor = AppColors.Primary
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Refresh"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Try Again")
            }
        }
    }
}

@Composable
fun WeatherContent(
    weatherData: WeatherResponse,
    onRefresh: () -> Unit
) {
    // Rotation and scale animation for weather icon
    var rotation by remember { mutableStateOf(0f) }
    LaunchedEffect(Unit) {
        while(true) {
            delay(3000)
            rotation += 360f
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .clip(RoundedCornerShape(24.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.9f),
            contentColor = AppColors.TextPrimary
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Location and Refresh Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Location
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location",
                        tint = AppColors.Primary,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = weatherData.name,
                        style = MaterialTheme.typography.titleLarge,
                        color = AppColors.TextPrimary,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                // Refresh Button
                IconButton(
                    onClick = onRefresh,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(AppColors.Primary.copy(alpha = 0.1f))
                        .padding(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh",
                        tint = AppColors.Primary
                    )
                }
            }

            // Current Date
            Text(
                text = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault()).format(Date()),
                style = MaterialTheme.typography.bodyMedium,
                color = AppColors.TextSecondary,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Temperature and Weather Icon
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                // Animated Weather Icon
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .graphicsLayer {
                            rotationZ = rotation
                        }
                        .clip(CircleShape)
                        .background(AppColors.Primary.copy(alpha = 0.1f))
                        .padding(16.dp)
                ) {
                    Image(
                        painter = painterResource(id = getWeatherIcon(weatherData.weather.firstOrNull()?.main)),
                        contentDescription = "Weather Icon",
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "${weatherData.main.temp.toInt()}°C",
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 56.sp
                    ),
                    color = AppColors.Primary
                )
            }

            // Weather Description
            Text(
                text = weatherData.weather.firstOrNull()?.description?.capitalize() ?: "No description",
                style = MaterialTheme.typography.titleMedium,
                color = AppColors.TextSecondary,
                modifier = Modifier.padding(vertical = 12.dp)
            )

            // Additional Weather Details
            WeatherDetailsSection(weatherData)
        }
    }
}

@Composable
fun WeatherDetailsSection(weatherData: WeatherResponse) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        WeatherDetailItem(
            icon = R.drawable.ic_humidity,
            label = "Humidity",
            value = "${weatherData.main.humidity}%"
        )
        WeatherDetailItem(
            icon = R.drawable.ic_wind,
            label = "Wind",
            value = "${weatherData.wind.speed} km/h"
        )
        WeatherDetailItem(
            icon = R.drawable.ic_pressure,
            label = "Pressure",
            value = "${weatherData.main.pressure} hPa"
        )
    }
}

@Composable
fun NoDataDisplay() {
    Text(
        text = "No weather data available",
        style = MaterialTheme.typography.bodyLarge,
        color = AppColors.TextSecondary,
        modifier = Modifier.padding(16.dp)
    )
}

// Helper function remains the same
fun getWeatherIcon(condition: String?): Int = when (condition?.lowercase()) {
    "clear" -> R.drawable.ic_sun
    "clouds" -> R.drawable.ic_cloudy
    "rain" -> R.drawable.ic_rain
    "thunderstorm" -> R.drawable.ic_storm
    "snow" -> R.drawable.ic_snow
    else -> R.drawable.ic_default_weather
}
package com.example.infolangit.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.infolangit.R
import com.example.infolangit.ui.theme.AppColors

@Composable
fun BackgroundScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background Image with Gradient Overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF3F51B5).copy(alpha = 0.7f),
                            Color(0xFF2196F3).copy(alpha = 0.9f)
                        )
                    )
                )
        ) {
            Image(
                painter = painterResource(id = R.drawable.background_biru),
                contentDescription = "Background",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.3f)
            )
        }

        // Content Overlay
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            // Welcome Title
            Text(
                text = stringResource(R.string.welcome_title),
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp
                ),
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Welcome Subtitle
            Text(
                text = stringResource(R.string.welcome_subtitle),
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.White.copy(alpha = 0.87f),
                    fontSize = 16.sp
                ),
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Features Container
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White.copy(alpha = 0.2f))
                    .padding(16.dp)
            ) {
                // Additional Welcome Content
                WelcomeFeatures()
            }
        }
    }
}

@Composable
fun WelcomeFeatures() {
    Column {
        WelcomeFeatureItem("🌞 Informasi Cuaca Terkini")
        Spacer(modifier = Modifier.height(8.dp))
        WelcomeFeatureItem("🌈 Prediksi Cuaca Akurat")
        Spacer(modifier = Modifier.height(8.dp))
        WelcomeFeatureItem("📍 Lokasi Tersimpan")
    }
}

@Composable
fun WelcomeFeatureItem(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(Color.White, shape = RoundedCornerShape(50))
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.White,
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}
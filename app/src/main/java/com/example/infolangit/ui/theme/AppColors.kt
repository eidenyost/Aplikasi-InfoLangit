package com.example.infolangit.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Custom color palette
object AppColors {
    val Primary = Color(0xFF2196F3)
    val Secondary = Color(0xFF03A9F4)
    val Background = Color(0xFFF5F5F5)
    val Surface = Color.White
    val OnPrimary = Color.White
    val TextPrimary = Color(0xFF212121)
    val TextSecondary = Color(0xFF757575)
    val Error = Color(0xFFD32F2F)
}

// Custom Color Scheme
private val LightColorScheme = lightColorScheme(
    primary = AppColors.Primary,
    secondary = AppColors.Secondary,
    background = AppColors.Background,
    surface = AppColors.Surface,
    onPrimary = AppColors.OnPrimary,
    error = AppColors.Error
)

@Composable
fun InfoLangitTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
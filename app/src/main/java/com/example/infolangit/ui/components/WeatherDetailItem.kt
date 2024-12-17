package com.example.infolangit.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.infolangit.ui.theme.AppColors

@Composable
fun WeatherDetailItem(
    @DrawableRes icon: Int,
    label: String,
    value: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(AppColors.Primary.copy(alpha = 0.1f))
            .padding(16.dp)
    ) {
        // Circular Background for Icon
        Box(
            modifier = Modifier
                .size(48.dp)
                .shadow(4.dp, shape = RoundedCornerShape(50))
                .background(Color.White, shape = RoundedCornerShape(50))
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = label,
                modifier = Modifier.size(32.dp),
                tint = AppColors.Primary
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = AppColors.TextSecondary,
            fontWeight = FontWeight.Medium
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = AppColors.TextPrimary,
            fontWeight = FontWeight.Bold
        )
    }
}
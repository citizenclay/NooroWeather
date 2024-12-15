package com.example.nooroweather.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.nooroweather.R
import com.example.nooroweather.domain.models.Weather

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun WeatherDetailsLayout(weather: Weather) {
    val iconUrl = if (weather.condition.startsWith("//")) {
        "https:${weather.condition}"
    } else {
        weather.condition
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        GlideImage(
            model = iconUrl,
            contentDescription = weather.conditionText,
            modifier = Modifier.zIndex(10.0f).size(100.dp),
            contentScale = ContentScale.FillBounds,
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = weather.name,
                style = MaterialTheme.typography.titleLarge.copy(color = Color.Black)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                painter = painterResource(R.drawable.baseline_near_me_24),
                contentDescription = "Location Icon",
                tint = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "${weather.temp}°",
            style = MaterialTheme.typography.displayMedium.copy(color = Color.Black)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier
                .fillMaxWidth(0.8f)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                WeatherDetailColumn(label = "Humidity", value = "${weather.humidity}%")
                WeatherDetailColumn(label = "UV", value = "${weather.uvIndex}")
                WeatherDetailColumn(label = "Feels Like", value = "${weather.feelsLike}°")
            }
        }
    }
}

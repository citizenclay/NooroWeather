package com.example.nooroweather.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nooroweather.domain.models.Weather

@Entity
data class WeatherEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 1,
    val name: String,
    val temp: Double,
    val condition: String,
    val conditionText: String,
    val humidity: Int,
    val uvIndex: Double,
    val feelsLike: Double
) {
    fun toWeather(): Weather {
        return Weather(
            name, temp, condition, conditionText, humidity, uvIndex, feelsLike
        )
    }
}

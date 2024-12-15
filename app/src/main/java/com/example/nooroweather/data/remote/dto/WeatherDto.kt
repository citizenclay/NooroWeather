package com.example.nooroweather.data.remote.dto

import com.example.nooroweather.data.local.entities.WeatherEntity
import kotlinx.serialization.Serializable

@Serializable
data class WeatherDto(
    val current: Current, val location: Location
) {
    fun toWeatherEntity(): WeatherEntity {
        return WeatherEntity(
            name = location.name,
            temp = current.temp_f,
            condition = current.condition.icon,
            conditionText = current.condition.text,
            humidity = current.humidity,
            uvIndex = current.uv,
            feelsLike = current.feelslike_f

        )
    }
}
package com.example.nooroweather.data.remote.dto

import com.example.nooroweather.data.local.entities.WeatherEntity

data class WeatherDto(
    val current: Current, val location: Location
) {
    fun toWeatherEntity(): WeatherEntity {
        return WeatherEntity(
            name = location.name,
            temp = current.temp_f,
            condition = current.condition.icon,
            humidity = current.humidity,
            uvIndex = current.uv,
            feelsLike = current.feelslike_f

        )
    }
}
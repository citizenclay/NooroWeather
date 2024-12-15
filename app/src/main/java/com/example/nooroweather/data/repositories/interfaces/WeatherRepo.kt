package com.example.nooroweather.data.repositories.interfaces

import com.example.nooroweather.data.local.entities.WeatherEntity
import com.example.nooroweather.data.remote.dto.WeatherDto
import com.example.nooroweather.domain.models.Weather
import com.example.nooroweather.util.Resource

interface WeatherRepo {
    suspend fun fetchWeather(query: String): Resource<WeatherDto>
    suspend fun getSavedWeather() : Weather?
    suspend fun updateWeather(weather: WeatherEntity)
}
package com.example.nooroweather.domain.models

data class Weather(
    val name: String,
    val temp: Double,
    val condition: String,
    val humidity: Int,
    val uvIndex: Int,
    val feelsLike: Double
)

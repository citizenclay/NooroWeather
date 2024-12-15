package com.example.nooroweather.domain.models

data class Weather(
    val name: String,
    val temp: Double,
    val condition: String,
    val conditionText: String,
    val humidity: Int,
    val uvIndex: Double,
    val feelsLike: Double
)

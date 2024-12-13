package com.example.nooroweather.data.remote.dto

data class Current(
    val cloud: Int,
    val condition: Condition,
    val feelslike_f: Double,
    val humidity: Int,
    val temp_f: Double,
    val uv: Int,
)
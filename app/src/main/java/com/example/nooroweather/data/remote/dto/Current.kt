package com.example.nooroweather.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class Current(
    val cloud: Int,
    val condition: Condition,
    val feelslike_f: Double,
    val humidity: Int,
    val temp_f: Double,
    val uv: Double,
)
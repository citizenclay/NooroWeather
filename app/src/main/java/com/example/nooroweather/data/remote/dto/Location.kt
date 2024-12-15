package com.example.nooroweather.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val lat: Double,
    val lon: Double,
    val name: String,
)
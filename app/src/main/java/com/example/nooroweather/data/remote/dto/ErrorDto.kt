package com.example.nooroweather.data.remote.dto

import kotlinx.serialization.Serializable



@Serializable
data class ErrorDto(
    val error: Error
)

@Serializable
data class Error(
    val code: Int,
    val message: String
)
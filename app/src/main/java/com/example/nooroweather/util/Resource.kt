package com.example.nooroweather.util

import com.example.nooroweather.data.remote.dto.ErrorDto

sealed class Resource<out T> {
    data class Success<out T>(val data: T?) : Resource<T>()
    data class Error<out T>(val data: T? = null, val error: ErrorDto) : Resource<T>()
    data class Loading<out T>(val data: T? = null) : Resource<T>()
}
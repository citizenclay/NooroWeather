package com.example.nooroweather.util

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error<out T>(val data: T? = null, val error: String) : Resource<T>()
    data class Loading<out T>(val data: T? = null) : Resource<T>()
}
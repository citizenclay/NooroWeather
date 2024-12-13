package com.example.nooroweather.util

sealed class NetworkResource<out T, out E> {
    data class Success<out T>(val data: T) : NetworkResource<T, Nothing>()
    data class Error<out E>(val error: E) : NetworkResource<Nothing, E>()
    data class Loading<out T>(val data: T? = null) : NetworkResource<T, Nothing>()
}
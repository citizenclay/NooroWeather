package com.example.nooroweather.data.remote.api

import com.example.nooroweather.data.remote.dto.WeatherDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApi {

    @GET("current.json")
    suspend fun getWeather(
        @Query("q") q: String,
        @Query("aqi") aqi: String = "no"
    ): Response<WeatherDto>

}
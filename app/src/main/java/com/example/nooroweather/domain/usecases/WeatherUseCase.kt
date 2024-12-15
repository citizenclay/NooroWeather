package com.example.nooroweather.domain.usecases

import com.example.nooroweather.data.remote.dto.Error
import com.example.nooroweather.data.remote.dto.ErrorDto
import com.example.nooroweather.data.repositories.interfaces.WeatherRepo
import com.example.nooroweather.domain.models.Weather
import com.example.nooroweather.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WeatherUseCase(
    private val weatherRepo: WeatherRepo
) {
    fun getSavedWeather(): Flow<Resource<Weather>> = flow {
        emit(Resource.Loading())
        weatherRepo.getSavedWeather().let {
            emit(Resource.Success(it))
        }
    }

    fun fetchWeather(query: String): Flow<Resource<Weather>> = flow {
        emit(Resource.Loading())
        weatherRepo.fetchWeather(query).let {
            if (it is Resource.Success) {
                val weatherEntity = it.data!!.toWeatherEntity()
                weatherRepo.updateWeather(weatherEntity)
                emit(Resource.Success(weatherEntity.toWeather()))
            } else if (it is Resource.Error) {
                emit(Resource.Error(error = it.error))
            } else {
                emit(Resource.Error(error = ErrorDto(Error(code = 0, message = "Unknown Error Occurred"))))
            }
        }
    }
}
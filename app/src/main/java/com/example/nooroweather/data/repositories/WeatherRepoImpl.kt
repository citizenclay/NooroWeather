package com.example.nooroweather.data.repositories

import com.example.nooroweather.data.local.dao.WeatherDao
import com.example.nooroweather.data.local.entities.WeatherEntity
import com.example.nooroweather.data.remote.api.WeatherApi
import com.example.nooroweather.data.remote.dto.Error
import com.example.nooroweather.data.remote.dto.ErrorDto
import com.example.nooroweather.data.remote.dto.WeatherDto
import com.example.nooroweather.data.repositories.interfaces.WeatherRepo
import com.example.nooroweather.domain.models.Weather
import com.example.nooroweather.util.Resource
import okhttp3.ResponseBody
import retrofit2.Converter

class WeatherRepoImpl(
    private val weatherApi: WeatherApi,
    private val weatherDao: WeatherDao,
    private val errorConverter: Converter<ResponseBody, ErrorDto>
) : WeatherRepo {
    override suspend fun fetchWeather(query: String): Resource<WeatherDto> {
        val response = weatherApi.getWeather(q = query)
        return if (response.isSuccessful && response.body() != null) {
            Resource.Success(response.body()!!)
        } else {
            val errorDto = response.errorBody()?.let { errorConverter.convert(it) }
            Resource.Error(error = errorDto?: ErrorDto(Error(code = 0, message = "Error response was null")))
        }
    }

    override suspend fun getSavedWeather(): Weather? {
        return weatherDao.getSavedWeather().let {
            it?.toWeather()
        }
    }


    override suspend fun updateWeather(weather: WeatherEntity) {
        weatherDao.saveWeather(weather)
    }

}
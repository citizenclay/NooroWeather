package com.example.nooroweather.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.nooroweather.data.local.entities.WeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weatherentity WHERE id == 1 LIMIT 1")
    fun getSavedWeather() : Flow<WeatherEntity>

    @Insert
    fun saveWeather(weatherEntity: WeatherEntity)

}
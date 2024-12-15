package com.example.nooroweather.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nooroweather.data.local.entities.WeatherEntity

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weatherentity WHERE id == 1 LIMIT 1")
    fun getSavedWeather() : WeatherEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWeather(weatherEntity: WeatherEntity)


}
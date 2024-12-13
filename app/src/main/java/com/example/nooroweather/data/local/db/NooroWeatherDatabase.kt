package com.example.nooroweather.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nooroweather.data.local.dao.WeatherDao
import com.example.nooroweather.data.local.entities.WeatherEntity

@Database(entities = [WeatherEntity::class], version = 1)
abstract class NooroWeatherDatabase: RoomDatabase() {

    abstract fun weatherDao(): WeatherDao
}
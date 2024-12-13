package com.example.nooroweather.di

import android.content.Context
import androidx.room.Room
import com.example.nooroweather.data.local.dao.WeatherDao
import com.example.nooroweather.data.local.db.NooroWeatherDatabase
import com.example.nooroweather.data.remote.api.WeatherApi
import com.example.nooroweather.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.cdimascio.dotenv.dotenv
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesWeatherApiKey(): String {
        val dotenv = dotenv {
            directory = "/assets"
            filename = "env" // instead of '.env', use 'env'
        }
        return dotenv["PLACES_API_KEY"]
    }

    @Provides
    @Singleton
    fun providesAuthInterceptor(token: String): Interceptor {
        return Interceptor { chain ->
            val url = chain.request().url().newBuilder().addQueryParameter("key", token).build()
            chain.proceed(chain.request().newBuilder().url(url).build())
        }
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Provides
    @Singleton
    fun providesRetrofitClient(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.WEATHER_URL).client(okHttpClient).build()
    }

    @Provides
    @Singleton
    fun providesWeatherApi(retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun providesNooroDatabase(@ApplicationContext context: Context): NooroWeatherDatabase {
        return Room.databaseBuilder(
            context, NooroWeatherDatabase::class.java, "nooro_weather_db"
        ).build()
    }

    @Provides
    @Singleton
    fun providesNooroDatabaseDao(nooroWeatherDatabase: NooroWeatherDatabase): WeatherDao {
        return nooroWeatherDatabase.weatherDao()
    }


}
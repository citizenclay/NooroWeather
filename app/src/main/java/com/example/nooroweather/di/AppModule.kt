package com.example.nooroweather.di

import android.content.Context
import androidx.room.Room
import com.example.nooroweather.data.local.dao.WeatherDao
import com.example.nooroweather.data.local.db.NooroWeatherDatabase
import com.example.nooroweather.data.remote.api.WeatherApi
import com.example.nooroweather.data.remote.dto.ErrorDto
import com.example.nooroweather.data.repositories.WeatherRepoImpl
import com.example.nooroweather.data.repositories.interfaces.WeatherRepo
import com.example.nooroweather.domain.usecases.WeatherUseCase
import com.example.nooroweather.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.cdimascio.dotenv.dotenv
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
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
        return dotenv["WEATHER_API_KEY"]
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
    fun provideErrorResponseConverter(retrofit: Retrofit): Converter<ResponseBody, ErrorDto> {
        return retrofit.responseBodyConverter(ErrorDto::class.java, emptyArray())
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Provides
    @Singleton
    fun providesRetrofitClient(okHttpClient: OkHttpClient): Retrofit {
        @Suppress("JSON_FORMAT_REDUNDANT")
        return Retrofit.Builder().baseUrl(Constants.WEATHER_URL)
            .addConverterFactory(Json {
                ignoreUnknownKeys = true
                isLenient = true
            }.asConverterFactory(MediaType.get("application/json"))).client(okHttpClient).build()
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
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun providesNooroDatabaseDao(nooroWeatherDatabase: NooroWeatherDatabase): WeatherDao {
        return nooroWeatherDatabase.weatherDao()
    }

    @Provides
    @Singleton
    fun providesWeatherRepository(
        weatherApi: WeatherApi,
        weatherDao: WeatherDao,
        errorConverter: Converter<ResponseBody, ErrorDto>
    ): WeatherRepo {
        return WeatherRepoImpl(weatherApi, weatherDao, errorConverter)
    }

    @Provides
    @Singleton
    fun providesWeatherUseCase(weatherRepo: WeatherRepo): WeatherUseCase {
        return WeatherUseCase(weatherRepo)
    }


}
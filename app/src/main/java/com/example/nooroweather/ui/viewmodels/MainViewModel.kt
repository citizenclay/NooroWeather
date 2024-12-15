package com.example.nooroweather.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nooroweather.domain.models.Weather
import com.example.nooroweather.domain.usecases.WeatherUseCase
import com.example.nooroweather.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val weatherUseCase: WeatherUseCase
) : ViewModel() {

    private val _weather = MutableStateFlow<Weather?>(null)
    val weather: StateFlow<Weather?> = _weather.onStart {
        getSavedWeather()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        null
    )

    private val _searchResults = MutableStateFlow<List<Weather>>(emptyList())
    val searchResults: StateFlow<List<Weather>> = _searchResults.asStateFlow()

    private val _toastText = MutableStateFlow<String>("")
    val toastText : StateFlow<String> = _toastText.asStateFlow()

    private fun getSavedWeather() {
        viewModelScope.launch(Dispatchers.IO) {
            weatherUseCase.getSavedWeather().collect {
                when (it) {
                    is Resource.Success -> {
                        _weather.value = it.data
                    }

                    is Resource.Error -> {
                        _toastText.value = it.error.error.message
                    }

                    is Resource.Loading -> {
                        // enable spinner
                    }
                }
            }
        }
    }

    fun fetchWeather(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherUseCase.fetchWeather(query).collect {
                when (it) {
                    is Resource.Success -> {
                        _weather.value = it.data
                    }

                    is Resource.Error -> {
                        // toast text
                        _toastText.value = it.error.error.message
                    }

                    is Resource.Loading -> {
                        _searchResults.value = emptyList()
                        // enable spinner
                    }
                }
            }
        }
    }
    fun selectWeatherOption(weather: Weather) {
        _weather.value = weather
        _searchResults.value = emptyList()
    }
    fun resetToastText(){
        _toastText.value = ""
    }
}
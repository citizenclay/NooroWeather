package com.example.nooroweather

import WeatherScreen
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import com.example.nooroweather.ui.theme.NooroWeatherTheme
import com.example.nooroweather.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NooroWeatherTheme {
                WeatherScreen(viewModel)
            }
        }
        viewModel.viewModelScope.launch(Dispatchers.Main) {
            viewModel.toastText.onEach {
                if (it.isNotEmpty()) {
                    Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
                    viewModel.resetToastText()
                }
            }.collect()
        }
    }
}

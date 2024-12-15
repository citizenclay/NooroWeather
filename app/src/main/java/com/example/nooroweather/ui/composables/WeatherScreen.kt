
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nooroweather.domain.models.Weather
import com.example.nooroweather.ui.composables.NoCitySelectedLayout
import com.example.nooroweather.ui.composables.SearchBar
import com.example.nooroweather.ui.composables.SearchResultsList
import com.example.nooroweather.ui.composables.WeatherDetailsLayout
import com.example.nooroweather.ui.viewmodels.MainViewModel

@Composable
fun WeatherScreen(
    viewModel: MainViewModel
) {
    val weather: State<Weather?> = viewModel.weather.collectAsState(null)
    val searchResults = viewModel.searchResults.collectAsState()
    val searchQuery = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SearchBar(
            query = searchQuery.value,
            onQueryChange = { searchQuery.value = it },
            onSearch = {viewModel.fetchWeather(searchQuery.value)}
        )
        if (searchResults.value.isNotEmpty()) {
            SearchResultsList(
                results = searchResults.value,
                onItemClick = { selectedWeather ->
                    viewModel.selectWeatherOption(selectedWeather)
                }
            )
        } else {
            Spacer(modifier = Modifier.height(40.dp))
            if (weather.value != null) {
                WeatherDetailsLayout(weather = weather.value!!)
            } else {
                NoCitySelectedLayout()
            }
        }


    }
}
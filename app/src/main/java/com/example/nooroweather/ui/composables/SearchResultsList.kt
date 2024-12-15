package com.example.nooroweather.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.nooroweather.domain.models.Weather

@Composable
fun SearchResultsList(results: List<Weather>, onItemClick: (Weather) -> Unit) {
    LazyColumn {
        items(results.size) { item ->
            Text(
                text = results[item].name,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClick(results[item]) }
                    .padding(16.dp),
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black)
            )
        }
    }
}
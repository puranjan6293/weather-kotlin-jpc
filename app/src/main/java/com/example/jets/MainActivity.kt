package com.example.jets

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jets.ui.theme.JetsTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetsTheme {
                WeatherApp()
            }
        }
    }
}

@Composable
fun WeatherApp() {
    var cityName by remember { mutableStateOf("") }
    var weatherData by remember { mutableStateOf<WeatherResponse?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val apiKey = "2a613b266533e4ae0d3b8673cc7bc366" // Replace with your API key
    val weatherRepository = WeatherRepository(apiKey)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(color = Color(0xFFefeff0))
    ) {

        BasicTextField(
            value = cityName,
            onValueChange = { cityName = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(3.dp)
                .border(width = 1.dp, color = Color.Blue)
                .background(color = Color.White,)
        ) {
            Text(text = cityName, fontSize = 30.sp)
        }

        Button(
            onClick = {
                coroutineScope.launch {
                    isLoading = true
                    val weather = weatherRepository.fetchWeather(cityName)
                    weatherData = weather
                    isLoading = false
                }
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(8.dp)
        ) {
            Text(text = "Get Weather")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            weatherData?.let {
                Text(text = "City: ${it.name}")
                Text(text = "Temperature: ${it.main.temp}°C")
                Text(text = "Pressure: ${it.main.pressure} hPa")
                Text(text = "Weather: ${it.weather[0].description}")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetsTheme {
        WeatherApp()
    }
}
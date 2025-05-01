package com.example.wheatherapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wheatherapp.network.WeatherApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainScreenViewModel : ViewModel() {

    private val repository = WeatherApiRepository()

    private val _weather = MutableStateFlow<String>("Loading")
    val weather: StateFlow<String> = _weather



    init {
        fetchWeather(50.0,50.0)
    }

    private fun fetchWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            val result = repository.currentWeather(lat, lon)
            _weather.value = result
        }
    }
}

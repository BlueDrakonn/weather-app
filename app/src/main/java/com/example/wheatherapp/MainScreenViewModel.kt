package com.example.wheatherapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.wheatherapp.data.CurrentWeatherResponse
import com.example.wheatherapp.network.WeatherApiRepository
import com.example.wheatherapp.network.WeatherApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainScreenViewModel() : ViewModel() {


    private  val repository = WeatherApiRepository()

    private val _weatherState = MutableStateFlow<CurrentWeatherResponse?>(null)
    val weatherState: StateFlow<CurrentWeatherResponse?> = _weatherState.asStateFlow()



    fun fetchWeather(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            val result = repository.currentWeather(latitude, longitude)

            if (result.isSuccess) {
                _weatherState.value = result.getOrNull()
            } else {
                _weatherState.value = null

            }

        }
    }
}

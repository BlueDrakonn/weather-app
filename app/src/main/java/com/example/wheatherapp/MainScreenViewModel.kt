package com.example.wheatherapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.wheatherapp.data.CurrentWeatherResponse
import com.example.wheatherapp.network.ResponseState
import com.example.wheatherapp.network.WeatherApiRepository
import com.example.wheatherapp.network.WeatherApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainScreenViewModel() : ViewModel() {


    private  val repository = WeatherApiRepository()

    private val _weatherState = MutableStateFlow<ResponseState?>(ResponseState.Loading)

    val weatherState: StateFlow<ResponseState?> = _weatherState.asStateFlow()



    suspend fun fetchWeather(latitude: Double, longitude: Double) {
        _weatherState.value = ResponseState.Loading
        try {
            val result = repository.getCurrentWeather(40.0,40.0)
            _weatherState.value = result
        } catch (e: Exception) {
            _weatherState.value = ResponseState.Error(e)
        }
    }




}

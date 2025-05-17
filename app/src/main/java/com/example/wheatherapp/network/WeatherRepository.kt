package com.example.wheatherapp.network

import android.util.Log

import com.example.wheatherapp.data.CurrentWeatherResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query



sealed class ResponseState {
    data object Loading : ResponseState()
    data class Success(val data: CurrentWeatherResponse) : ResponseState()
    data class Error(val exception: Exception) : ResponseState()
}

interface WeatherApiService {
    @GET("current.json")
    suspend fun getCurrentWeather(
        @Query("key") apiKey: String,
        @Query("q") location: String,
        @Query("aqi") aqi: String = "no"
    ): CurrentWeatherResponse
}



class WeatherApiRepository() {

    private val API_KEY = "4fdea4d317c64940b3f130345252502"


    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val apiService: WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }


    suspend fun getCurrentWeather(latitude: Double, longitude: Double): ResponseState {
        return try {
            val response = apiService.getCurrentWeather(API_KEY, "$latitude,$longitude")
            Log.d("WATHER_SUCCESS","$response")
            ResponseState.Success(response)
            //Result.success(response)
        } catch (e: Exception) {
            Log.d("WEATHER_ERROR", "$e ")
            //Result.failure(e)
            ResponseState.Error(e)
        }
    }




}
package com.example.wheatherapp.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


class WeatherApiRepository() {

    private val API_KEY = "4fdea4d317c64940b3f130345252502"



    private val client: OkHttpClient by lazy {
        OkHttpClient()
    }


    suspend fun currentWeather(latitude: Double, longitude: Double): String = withContext(
        Dispatchers.IO) {
        val request = Request.Builder()
            .url("https://api.weatherapi.com/v1/current.json?key=$API_KEY&q=$latitude,$longitude&aqi=no")
            .build()

        try {
            val response = client.newCall(request).execute()
            if (!response.isSuccessful) {
                return@withContext "неудачный запрос"
            }
            return@withContext response.body?.string().toString()
        } catch (e: IOException) {
            return@withContext "ошибка: ${e.message}"
        }
    }




}
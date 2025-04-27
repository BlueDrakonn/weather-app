package com.example.wheatherapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

import com.example.wheatherapp.ui.theme.WheatherAppTheme
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WheatherAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var text_weather by remember { mutableStateOf("") }
    val API_KEY = "4fdea4d317c64940b3f130345252502"

    val latitude = 54
    val longitude = 54
    //"http://api.weatherapi.com/v1/current.json?key=$API_KEY&q=$latitude,$longitude"
    val client = OkHttpClient()
    val request = Request.Builder()
        .url("https://api.weatherapi.com/v1/current.json?key=4fdea4d317c64940b3f130345252502&q=London&aqi=no")
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.d("EXSE", "$e")
            text_weather = "ошибка $e"

        }

        override fun onResponse(call: Call, response: Response) {
            response.use { response ->

                if (!response.isSuccessful) text_weather ="неудачный запрос" else text_weather =
                    response.body?.string().toString()

            }
        }

    })

    Text(
        text = "$text_weather",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WheatherAppTheme {
        Greeting("Android")
    }
}
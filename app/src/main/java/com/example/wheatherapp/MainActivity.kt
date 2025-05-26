package com.example.wheatherapp

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wheatherapp.network.ResponseState

import com.example.wheatherapp.ui.theme.WheatherAppTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



class MainActivity : ComponentActivity() {

    private  val viewModel: MainScreenViewModel by viewModels()

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WheatherAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WeatherScreen(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }




        fun getLastLocationOnceAndFetchWeather() {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        val latitude = location.latitude
                        val longitude = location.longitude

                        lifecycleScope.launch {
                            repeatOnLifecycle(Lifecycle.State.STARTED) {
                                viewModel.fetchWeather(latitude, longitude)
                            }
                        }
                    } else {
                        Toast.makeText(this, "Не удалось получить локацию", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Ошибка получения локации", Toast.LENGTH_SHORT).show()
                }
        }


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        when {
            ContextCompat.checkSelfPermission(
                this, ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                getLastLocationOnceAndFetchWeather()

                Log.d("PERMISSION", "ALREADY")
            }

            else -> {
                requestPermissionLauncher = registerForActivityResult(
                    ActivityResultContracts.RequestPermission()
                ) { isGranted: Boolean ->
                    if (isGranted) {
                        //Toast.makeText(this, "Разрешение предоставлено", Toast.LENGTH_SHORT).show()
                        Log.d("PERMISSION", "DONE")
                        getLastLocationOnceAndFetchWeather()


                    } else {

                        Toast.makeText(this, "Разрешение отклонено", Toast.LENGTH_SHORT).show()
                        // Показываем объяснение или делаем что-то ещё
                    }
                }

                requestPermissionLauncher.launch(ACCESS_FINE_LOCATION)
            }

        }


    }
}




@Composable
fun WeatherScreen(viewModel: MainScreenViewModel, modifier: Modifier = Modifier) {


    val weather by viewModel.weatherState.collectAsState()


    when (val state = weather) {
        is ResponseState.Error ->
            Text(text = "error",
                modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
            )
        ResponseState.Loading -> CircularProgressIndicator()
        is ResponseState.Success ->
            Text(text = "${state.data}",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
            )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WheatherAppTheme {
        WeatherScreen(viewModel = viewModel())
    }
}
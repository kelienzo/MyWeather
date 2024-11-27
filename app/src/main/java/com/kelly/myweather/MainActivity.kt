package com.kelly.myweather

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.kelly.core.common.Constants
import com.kelly.core.common.SharedPrefManager
import com.kelly.core.common.connectivity.NetworkObserver
import com.kelly.myweather.navigation.MyWeatherNavGraphs
import com.kelly.myweather.navigation.MyWeatherNavHost
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var networkObserver: NetworkObserver

    @Inject
    lateinit var myWeatherNavGraphs: MyWeatherNavGraphs

    @Inject
    lateinit var sharedPrefManager: SharedPrefManager

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                sharedPrefManager.saveBooleanData(Constants.IS_LOCATION_GRANTED, true)
            }
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
        setContent {
            val isConnected by networkObserver.isConnected.collectAsStateWithLifecycle()
            val snackbarHostState = remember { SnackbarHostState() }

            fun showSnackBarMessage(show: suspend () -> Unit) {
                lifecycleScope.launch { show() }
            }

            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                MyWeatherNavHost(
                    modifier = Modifier.padding(innerPadding),
                    myWeatherNavGraphs = myWeatherNavGraphs
                )
            }

            showSnackBarMessage {
                snackbarHostState.run {
                    if (isConnected.not()) {
                        showSnackbar(
                            message = "No internet connection",
                            duration = SnackbarDuration.Indefinite,
                            withDismissAction = true
                        )
                    } else {
                        currentSnackbarData?.dismiss()
                    }
                }
            }
        }
    }
}
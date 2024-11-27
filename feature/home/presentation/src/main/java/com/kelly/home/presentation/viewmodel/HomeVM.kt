package com.kelly.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelly.core.common.location.LocationHandler
import com.kelly.home.data.model.CurrentWeatherResponse
import com.kelly.home.data.repository.CurrentWeatherRepository
import com.kelly.home.presentation.ui.HomeEvent
import com.kelly.home.presentation.viewmodel.state.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(
    private val currentWeatherRepository: CurrentWeatherRepository,
    private val locationHandler: LocationHandler
) : ViewModel() {

    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState = _homeUiState.asStateFlow()

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.GetCurrentWeatherData -> viewModelScope.launch {
                locationHandler.getCurrentLocation()?.let { location ->
                    getCurrentLocationWeather(
                        latitude = location.latitude,
                        longitude = location.longitude
                    )
                }
                    ?: updateHomeUiState(errorMessage = "Location needed to get current weather condition")
            }

            else -> {}
        }
    }


    private fun getCurrentLocationWeather(
        latitude: Double?,
        longitude: Double?
    ) {
        currentWeatherRepository.getCurrentLocationWeather(latitude, longitude)
            .onStart {
                updateHomeUiState(isLoading = true)
            }.onEach { result ->
                result.onSuccess {
                    updateHomeUiState(response = it)
                }.onFailure {
                    updateHomeUiState(errorMessage = it.message)
                }
            }.launchIn(viewModelScope)
    }

    private fun updateHomeUiState(
        isLoading: Boolean = false,
        errorMessage: String? = null,
        response: CurrentWeatherResponse? = null
    ) {
        _homeUiState.value = HomeUiState(
            isLoading, errorMessage, response
        )
    }
}
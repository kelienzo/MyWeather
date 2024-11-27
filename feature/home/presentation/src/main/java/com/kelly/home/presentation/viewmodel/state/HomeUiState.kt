package com.kelly.home.presentation.viewmodel.state

import com.kelly.home.data.model.CurrentWeatherResponse

data class HomeUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val response: CurrentWeatherResponse? = null
)

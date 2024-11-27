package com.kelly.forcast.presentation.viewmodel.state

import com.kelly.forecast.data.model.ForeCastResponse

data class ForeCastUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val response: ForeCastResponse? = null
)

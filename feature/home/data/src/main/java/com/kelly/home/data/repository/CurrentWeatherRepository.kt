package com.kelly.home.data.repository

import com.kelly.home.data.model.CurrentWeatherResponse
import kotlinx.coroutines.flow.Flow

interface CurrentWeatherRepository {

    fun getCurrentLocationWeather(
        latitude: Double?,
        longitude: Double?
    ): Flow<Result<CurrentWeatherResponse>>
}
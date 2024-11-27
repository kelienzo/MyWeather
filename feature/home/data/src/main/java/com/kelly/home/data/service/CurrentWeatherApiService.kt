package com.kelly.home.data.service

import com.kelly.home.data.model.CurrentWeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrentWeatherApiService {

    @GET("data/2.5/weather")
    suspend fun getCurrentWeatherData(
        @Query("lat") latitude: Double?,
        @Query("lon") longitude: Double?,
        @Query("units") units: String? = "metric",
        @Query("appid") apiKey: String
    ): Response<CurrentWeatherResponse>
}
package com.kelly.forecast.data.service

import com.kelly.forecast.data.model.ForeCastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ForeCastApiService {
    @GET("data/2.5/forecast")
    suspend fun getForCast(
        @Query("id") cityId: Int,
        @Query("units") units: String = "metric",
        @Query("appid") apiKey: String,
    ): Response<ForeCastResponse>
}
package com.kelly.search.data.service

import com.kelly.search.data.model.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchCityApiService {

    @GET("data/2.5/forecast")
    suspend fun searchCity(
        @Query("q") search: String,
        @Query("units") units: String? = "metric",
        @Query("appid") apiKey: String
    ): Response<SearchResponse>
}
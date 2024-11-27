package com.kelly.home.data.repository

import com.kelly.core.common.model.ErrorModel
import com.kelly.core.common.utils.fromJson
import com.kelly.home.data.model.CurrentWeatherResponse
import com.kelly.home.data.service.CurrentWeatherApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CurrentWeatherRepositoryImpl @Inject constructor(
    private val currentWeatherApiService: CurrentWeatherApiService,
    private val coroutineDispatcher: CoroutineDispatcher,
    private val apiKey: String
) : CurrentWeatherRepository {

    override fun getCurrentLocationWeather(
        latitude: Double?,
        longitude: Double?
    ): Flow<Result<CurrentWeatherResponse>> = flow {
        val response = currentWeatherApiService.getCurrentWeatherData(
            latitude = latitude,
            longitude = longitude,
            apiKey = apiKey
        )
        if (response.isSuccessful) {
            response.body()?.let { res ->
                emit(Result.success(res))
            } ?: emit(Result.failure(Throwable("An error occurred.")))
        } else {
            val error = response.errorBody()?.string().fromJson<ErrorModel>()
            emit(Result.failure(Throwable(error?.message ?: "An error occurred.")))
        }
    }.catch {
        it.printStackTrace()
        emit(Result.failure(Throwable("An exception occurred.")))
    }.flowOn(coroutineDispatcher)
}
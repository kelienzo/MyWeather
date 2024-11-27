package com.kelly.home.data.repository

import com.kelly.home.data.model.CurrentWeatherResponse
import com.kelly.home.data.service.CurrentWeatherApiService
import junit.framework.TestCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.Response

class CurrentWeatherRepositoryImplTest {

    private lateinit var currentWeatherApiService: CurrentWeatherApiService
    private lateinit var coroutineDispatcher: CoroutineDispatcher
    private lateinit var currentWeatherRepository: CurrentWeatherRepository

    @Before
    fun setUp() {
        currentWeatherApiService = mock()
        coroutineDispatcher = Dispatchers.IO
        currentWeatherRepository = CurrentWeatherRepositoryImpl(
            currentWeatherApiService,
            coroutineDispatcher,
            ""
        )
    }

    @Test
    fun test_returns_success() = runTest {
        `when`(
            currentWeatherApiService.getCurrentWeatherData(
                latitude = 23.2,
                longitude = 23.3,
                apiKey = ""
            )
        ).thenReturn(Response.success(200, currentWeatherResponse()))

        val response =
            currentWeatherRepository.getCurrentLocationWeather(latitude = 23.2, longitude = 23.3)
                .first().getOrThrow()

        TestCase.assertEquals(currentWeatherResponse(), response)
    }

    @Test
    fun test_returns_success_null() = runTest {
        `when`(
            currentWeatherApiService.getCurrentWeatherData(
                latitude = 23.2,
                longitude = 23.3,
                apiKey = ""
            )
        ).thenReturn(Response.success(null))

        val response =
            currentWeatherRepository.getCurrentLocationWeather(latitude = 23.2, longitude = 23.3)
                .first().exceptionOrNull()?.message

        TestCase.assertEquals("An error occurred.", response)
    }

    @Test
    fun test_returns_failed() = runTest {
        `when`(
            currentWeatherApiService.getCurrentWeatherData(
                latitude = 23.2,
                longitude = 23.3,
                apiKey = ""
            )
        ).thenReturn(Response.error(400, "".toResponseBody(null)))

        val response =
            currentWeatherRepository.getCurrentLocationWeather(latitude = 23.2, longitude = 23.3)
                .first().exceptionOrNull()?.message

        TestCase.assertEquals("An error occurred.", response)
    }

    @Test
    fun test_returns_exception() = runTest {
        `when`(
            currentWeatherApiService.getCurrentWeatherData(
                latitude = 23.2,
                longitude = 23.3,
                apiKey = ""
            )
        ).thenThrow(RuntimeException("An exception occurred."))

        val response =
            currentWeatherRepository.getCurrentLocationWeather(latitude = 23.2, longitude = 23.3)
                .first().exceptionOrNull()?.message

        TestCase.assertEquals("An exception occurred.", response)
    }

    private fun currentWeatherResponse() = CurrentWeatherResponse(
        base = null,
        clouds = null,
        cod = null,
        coord = null,
        dt = null,
        id = null,
        main = null,
        name = null,
        rain = null,
        sys = null,
        timezone = null,
        visibility = null,
        weather = null,
        wind = null
    )
}
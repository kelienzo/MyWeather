package com.kelly.feature.search.data.repository

import com.kelly.search.data.model.City
import com.kelly.search.data.model.Coord
import com.kelly.search.data.model.SearchResponse
import com.kelly.search.data.repository.SearchCityRepository
import com.kelly.search.data.repository.SearchCityRepositoryImpl
import com.kelly.search.data.service.SearchCityApiService
import junit.framework.TestCase.assertEquals
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

class SearchCityRepositoryImplTest {

    private lateinit var searchCityApiService: SearchCityApiService
    private lateinit var coroutineDispatcher: CoroutineDispatcher
    private lateinit var searchCityRepository: SearchCityRepository

    @Before
    fun setUp() {
        searchCityApiService = mock()
        coroutineDispatcher = Dispatchers.IO
        searchCityRepository = SearchCityRepositoryImpl(
            searchCityApiService,
            coroutineDispatcher,
            ""
        )
    }

    @Test
    fun test_returns_success() = runTest {
        `when`(searchCityApiService.searchCity("London", apiKey = ""))
            .thenReturn(Response.success(200, searchResponse()))

        val response = searchCityRepository.searchCity("London").first().getOrThrow()

        assertEquals(searchResponse(), response)
    }

    @Test
    fun test_returns_success_null() = runTest {
        `when`(searchCityApiService.searchCity("London", apiKey = ""))
            .thenReturn(Response.success(null))

        val response = searchCityRepository.searchCity("London").first().exceptionOrNull()?.message

        assertEquals("An error occurred.", response)
    }

    @Test
    fun test_returns_failed() = runTest {
        `when`(searchCityApiService.searchCity("London", apiKey = ""))
            .thenReturn(Response.error(400, "".toResponseBody(null)))

        val response = searchCityRepository.searchCity("London").first().exceptionOrNull()?.message

        assertEquals("An error occurred.", response)
    }

    @Test
    fun test_returns_exception() = runTest {
        `when`(searchCityApiService.searchCity("London", apiKey = ""))
            .thenThrow(RuntimeException("An exception occurred."))

        val response = searchCityRepository.searchCity("London").first().exceptionOrNull()?.message

        assertEquals("An exception occurred.", response)
    }

    private fun searchResponse() = SearchResponse(
        city = City(
            coord = Coord(lat = 12.2, lon = 32.2),
            country = "",
            id = 23,
            name = "London",
            population = 3,
            sunrise = 32,
            sunset = 23,
            timezone = 23
        ),
        cnt = 3,
        cod = "",
        message = 2
    )
}
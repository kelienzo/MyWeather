@file:OptIn(ExperimentalCoroutinesApi::class)

package com.kelly.search.presentation.viewmodel

import com.kelly.search.data.model.City
import com.kelly.search.data.model.Coord
import com.kelly.search.data.model.SearchResponse
import com.kelly.search.data.repository.SearchCityRepositoryImpl
import com.kelly.search.presentation.ui.SearchEvent
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class SearchVMTest {

    @get:Rule
    val mainDispatcher = MainDispatcher()

    private val searchCityRepository: SearchCityRepositoryImpl = mock()
    private lateinit var viewModel: SearchVM

    @Before
    fun setUp() {
        viewModel = SearchVM(searchCityRepository)
    }

    @Test
    fun test_success_search() = runTest {
        `when`(searchCityRepository.searchCity("London"))
            .thenReturn(flowOf(Result.success(searchResponse())))
        viewModel.onEvent(SearchEvent.OnSearch("London"))
        assertEquals(searchResponse(), viewModel.searchUiState.value.response)
    }

    @Test
    fun test_error_search() = runTest {
        `when`(searchCityRepository.searchCity("London"))
            .thenReturn(flowOf(Result.failure(Throwable("error"))))
        viewModel.onEvent(SearchEvent.OnSearch("London"))
        assertEquals("error", viewModel.searchUiState.value.errorMessage)
    }

    class MainDispatcher(private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()) :
        TestWatcher() {

        @OptIn(ExperimentalCoroutinesApi::class)
        override fun starting(description: Description?) {
            Dispatchers.setMain(testDispatcher)
        }

        override fun finished(description: Description?) {
            Dispatchers.resetMain()
        }
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
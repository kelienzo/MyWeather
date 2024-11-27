package com.kelly.search.presentation.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.kelly.core.common.navigation.NavigationGraph
import com.kelly.core.common.navigation.Routes
import com.kelly.search.presentation.ui.SearchEvent
import com.kelly.search.presentation.ui.SearchScreen
import com.kelly.search.presentation.viewmodel.SearchVM

interface SearchNavGraph : NavigationGraph

class SearchNavGraphImpl : SearchNavGraph {
    override fun initializeGraph(
        navHostController: NavHostController,
        navGraphBuilder: NavGraphBuilder
    ) {
        navGraphBuilder.composable<Routes.SearchNavScreen> {
            val viewModel = hiltViewModel<SearchVM>()
            val searchUiState by viewModel.searchUiState.collectAsStateWithLifecycle()

            SearchScreen(
                searchUiState = searchUiState,
                onUiEvent = { event ->
                    when (event) {
                        SearchEvent.OnBack -> navHostController.navigateUp()
                        is SearchEvent.NavigateToForeCast -> navHostController.navigate(
                            Routes.ForeCastNavScreen(event.cityId)
                        )

                        else -> viewModel.onEvent(event)
                    }
                }
            )
        }
    }
}
package com.kelly.home.presentation.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.kelly.core.common.SharedPrefManager
import com.kelly.core.common.navigation.NavigationGraph
import com.kelly.core.common.navigation.Routes
import com.kelly.home.presentation.ui.HomeEvent
import com.kelly.home.presentation.ui.HomeScreen
import com.kelly.home.presentation.viewmodel.HomeVM
import javax.inject.Inject

interface HomeNavGraph : NavigationGraph

class HomeNavGraphImpl @Inject constructor(
    private val sharedPrefManager: SharedPrefManager
) : HomeNavGraph {

    override fun initializeGraph(
        navHostController: NavHostController,
        navGraphBuilder: NavGraphBuilder
    ) {
        navGraphBuilder.composable<Routes.HomeNavScreen> {
            val viewModel = hiltViewModel<HomeVM>()
            val homeUiState by viewModel.homeUiState.collectAsStateWithLifecycle()
            val isLocationGranted by sharedPrefManager.isLocationGranted.collectAsStateWithLifecycle(
                false
            )

            HomeScreen(
                homeUiState = homeUiState,
                isLocationGranted = isLocationGranted,
                onUiEvent = { event ->
                    when (event) {
                        HomeEvent.NavigateToFavorites -> navHostController.navigate(Routes.FavoritesNavScreen)
                        HomeEvent.NavigateToSearch -> navHostController.navigate(Routes.SearchNavScreen)
                        else -> viewModel.onEvent(event)
                    }
                }
            )
        }
    }
}
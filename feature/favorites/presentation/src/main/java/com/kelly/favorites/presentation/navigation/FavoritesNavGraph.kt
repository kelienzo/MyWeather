package com.kelly.favorites.presentation.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.kelly.core.common.navigation.NavigationGraph
import com.kelly.core.common.navigation.Routes
import com.kelly.favorites.presentation.ui.FavoritesEvent
import com.kelly.favorites.presentation.ui.FavoritesScreen
import com.kelly.favorites.presentation.viewmodel.FavoritesVM

interface FavoritesNavGraph : NavigationGraph

class FavoritesNavGraphImpl : FavoritesNavGraph {
    override fun initializeGraph(
        navHostController: NavHostController,
        navGraphBuilder: NavGraphBuilder
    ) {
        navGraphBuilder.composable<Routes.FavoritesNavScreen> {
            val viewModel = hiltViewModel<FavoritesVM>()
            val favoritesList by viewModel.favoritesUiState.collectAsStateWithLifecycle()

            FavoritesScreen(
                favorites = favoritesList,
                onUiEvent = { event ->
                    when (event) {
                        FavoritesEvent.OnBack -> navHostController.navigateUp()
                        is FavoritesEvent.OnViewForeCast -> navHostController.navigate(
                            Routes.ForeCastNavScreen(cityId = event.cityId)
                        )

                        else -> viewModel.onEvent(event)
                    }
                }
            )
        }
    }
}
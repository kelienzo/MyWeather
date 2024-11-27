package com.kelly.forcast.presentation.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.kelly.core.common.navigation.NavigationGraph
import com.kelly.core.common.navigation.Routes
import com.kelly.forcast.presentation.ui.ForeCastEvent
import com.kelly.forcast.presentation.ui.ForeCastScreen
import com.kelly.forcast.presentation.viewmodel.ForeCastVM

interface ForeCastNavGraph : NavigationGraph

class ForeCastNavGraphImpl : ForeCastNavGraph {
    override fun initializeGraph(
        navHostController: NavHostController,
        navGraphBuilder: NavGraphBuilder
    ) {
        navGraphBuilder.composable<Routes.ForeCastNavScreen> {
            val viewModel = hiltViewModel<ForeCastVM>()
            val foreCastUiState by viewModel.foreCastUiState.collectAsStateWithLifecycle()
            val favorites by viewModel.favorites.collectAsStateWithLifecycle()

            ForeCastScreen(
                cityId = it.toRoute<Routes.ForeCastNavScreen>().cityId,
                favorites = favorites,
                foreCastUiState = foreCastUiState,
                onUiEvent = { event ->
                    when (event) {
                        ForeCastEvent.OnBack -> navHostController.navigateUp()
                        else -> viewModel.onEvent(event)
                    }
                }
            )
        }
    }
}
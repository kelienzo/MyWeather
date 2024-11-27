package com.kelly.myweather.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.kelly.core.common.navigation.Routes

@Composable
fun MyWeatherNavHost(modifier: Modifier = Modifier, myWeatherNavGraphs: MyWeatherNavGraphs) {
    val navHostController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = Routes.HomeNavScreen
    ) {
        myWeatherNavGraphs.run {
            homeNavGraph.initializeGraph(
                navHostController = navHostController,
                navGraphBuilder = this@NavHost
            )

            favoritesNavGraph.initializeGraph(
                navHostController = navHostController,
                navGraphBuilder = this@NavHost
            )

            searchNavGraph.initializeGraph(
                navHostController = navHostController,
                navGraphBuilder = this@NavHost
            )

            foreCastNavGraph.initializeGraph(
                navHostController = navHostController,
                navGraphBuilder = this@NavHost
            )
        }
    }
}
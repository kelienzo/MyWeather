package com.kelly.myweather.navigation

import com.kelly.favorites.presentation.navigation.FavoritesNavGraph
import com.kelly.forcast.presentation.navigation.ForeCastNavGraph
import com.kelly.home.presentation.navigation.HomeNavGraph
import com.kelly.search.presentation.navigation.SearchNavGraph

data class MyWeatherNavGraphs(
    val homeNavGraph: HomeNavGraph,
    val favoritesNavGraph: FavoritesNavGraph,
    val searchNavGraph: SearchNavGraph,
    val foreCastNavGraph: ForeCastNavGraph
)

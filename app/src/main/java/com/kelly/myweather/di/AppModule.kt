package com.kelly.myweather.di

import com.kelly.favorites.presentation.navigation.FavoritesNavGraph
import com.kelly.forcast.presentation.navigation.ForeCastNavGraph
import com.kelly.home.presentation.navigation.HomeNavGraph
import com.kelly.myweather.navigation.MyWeatherNavGraphs
import com.kelly.search.presentation.navigation.SearchNavGraph
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMyWeatherNavGraphs(
        homeNavGraph: HomeNavGraph,
        favoritesNavGraph: FavoritesNavGraph,
        searchNavGraph: SearchNavGraph,
        foreCastNavGraph: ForeCastNavGraph
    ): MyWeatherNavGraphs {
        return MyWeatherNavGraphs(homeNavGraph, favoritesNavGraph, searchNavGraph, foreCastNavGraph)
    }
}
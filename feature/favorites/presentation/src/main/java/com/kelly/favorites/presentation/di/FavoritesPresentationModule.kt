package com.kelly.favorites.presentation.di

import com.kelly.favorites.presentation.navigation.FavoritesNavGraph
import com.kelly.favorites.presentation.navigation.FavoritesNavGraphImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FavoritesPresentationModule {

    @Singleton
    @Provides
    fun provideFavoritesNavGraph(): FavoritesNavGraph {
        return FavoritesNavGraphImpl()
    }
}
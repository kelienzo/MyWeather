package com.kelly.favorites.data.di

import com.kelly.core.database.MyWeatherDB
import com.kelly.favorites.data.repository.FavoritesRepository
import com.kelly.favorites.data.repository.FavoritesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FavoritesDataModule {

    @Singleton
    @Provides
    fun provideFavoritesRepository(myWeatherDB: MyWeatherDB): FavoritesRepository {
        return FavoritesRepositoryImpl(myWeatherDB)
    }
}
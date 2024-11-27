package com.kelly.home.presentation.di

import com.kelly.core.common.SharedPrefManager
import com.kelly.home.presentation.navigation.HomeNavGraph
import com.kelly.home.presentation.navigation.HomeNavGraphImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomePresentationModule {

    @Singleton
    @Provides
    fun provideHomeNavGraph(sharedPrefManager: SharedPrefManager): HomeNavGraph {
        return HomeNavGraphImpl(sharedPrefManager)
    }
}
package com.kelly.forcast.presentation.di

import com.kelly.forcast.presentation.navigation.ForeCastNavGraph
import com.kelly.forcast.presentation.navigation.ForeCastNavGraphImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ForeCastPresentationModule {

    @Singleton
    @Provides
    fun provideForeCastNavGraph(): ForeCastNavGraph {
        return ForeCastNavGraphImpl()
    }
}
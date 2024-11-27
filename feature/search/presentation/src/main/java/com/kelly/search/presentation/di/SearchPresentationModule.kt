package com.kelly.search.presentation.di

import com.kelly.search.presentation.navigation.SearchNavGraph
import com.kelly.search.presentation.navigation.SearchNavGraphImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchPresentationModule {

    @Singleton
    @Provides
    fun provideSearchNavGraph(): SearchNavGraph {
        return SearchNavGraphImpl()
    }
}
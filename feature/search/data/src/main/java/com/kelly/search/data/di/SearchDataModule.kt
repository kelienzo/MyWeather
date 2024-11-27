package com.kelly.search.data.di

import com.kelly.core.common.Constants
import com.kelly.search.data.repository.SearchCityRepository
import com.kelly.search.data.repository.SearchCityRepositoryImpl
import com.kelly.search.data.service.SearchCityApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchDataModule {

    @Singleton
    @Provides
    fun provideSearchCityApiService(
        okHttpClient: OkHttpClient
    ): SearchCityApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build().create(SearchCityApiService::class.java)

    }

    @Singleton
    @Provides
    fun provideSearchCityRepository(
        searchCityApiService: SearchCityApiService,
        coroutineDispatcher: CoroutineDispatcher,
        apiKey: String
    ): SearchCityRepository {
        return SearchCityRepositoryImpl(searchCityApiService, coroutineDispatcher, apiKey)
    }
}
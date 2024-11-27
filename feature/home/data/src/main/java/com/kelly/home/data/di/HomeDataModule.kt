package com.kelly.home.data.di

import com.kelly.core.common.Constants
import com.kelly.home.data.repository.CurrentWeatherRepository
import com.kelly.home.data.repository.CurrentWeatherRepositoryImpl
import com.kelly.home.data.service.CurrentWeatherApiService
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
object HomeDataModule {

    @Singleton
    @Provides
    fun provideCurrentWeatherApiService(
        okHttpClient: OkHttpClient
    ): CurrentWeatherApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build().create(CurrentWeatherApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideCurrentLocationWeatherRepo(
        currentWeatherApiService: CurrentWeatherApiService,
        coroutineDispatcher: CoroutineDispatcher,
        apiKey: String
    ): CurrentWeatherRepository {
        return CurrentWeatherRepositoryImpl(currentWeatherApiService, coroutineDispatcher, apiKey)
    }
}
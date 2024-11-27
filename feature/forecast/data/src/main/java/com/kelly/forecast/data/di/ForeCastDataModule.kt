package com.kelly.forecast.data.di

import com.kelly.core.common.Constants
import com.kelly.core.database.MyWeatherDB
import com.kelly.forecast.data.repository.ForeCastRepository
import com.kelly.forecast.data.repository.ForeCastRepositoryImpl
import com.kelly.forecast.data.service.ForeCastApiService
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
object ForeCastDataModule {

    @Singleton
    @Provides
    fun provideForCastApiService(okHttpClient: OkHttpClient): ForeCastApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build().create(ForeCastApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideForCastRepository(
        myWeatherDB: MyWeatherDB,
        foreCastApiService: ForeCastApiService,
        coroutineDispatcher: CoroutineDispatcher,
        apiKey: String
    ): ForeCastRepository {
        return ForeCastRepositoryImpl(myWeatherDB, foreCastApiService, coroutineDispatcher, apiKey)
    }
}
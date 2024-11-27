package com.kelly.core.database.di

import android.content.Context
import androidx.room.Room
import com.kelly.core.database.MyWeatherDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideMyWeatherDatabase(@ApplicationContext context: Context): MyWeatherDB {
        return Room.databaseBuilder(context, MyWeatherDB::class.java, "my_weather_db").build()
    }
}
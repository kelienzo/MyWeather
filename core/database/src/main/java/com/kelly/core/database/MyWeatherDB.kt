package com.kelly.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kelly.core.database.converter.MyWeatherConverter
import com.kelly.core.database.dao.FavoritesDao
import com.kelly.core.database.dao.ForeCastDao
import com.kelly.core.database.entities.FavoritesEntity
import com.kelly.core.database.entities.ForeCastEntity

@Database(entities = [ForeCastEntity::class, FavoritesEntity::class], version = 1)
@TypeConverters(MyWeatherConverter::class)
abstract class MyWeatherDB : RoomDatabase() {

    abstract val foreCastDao: ForeCastDao
    abstract val favoritesDao: FavoritesDao
}
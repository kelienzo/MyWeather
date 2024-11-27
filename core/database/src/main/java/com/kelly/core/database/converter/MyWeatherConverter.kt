package com.kelly.core.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kelly.core.common.utils.fromJson
import com.kelly.core.common.utils.toJson
import com.kelly.core.database.entities.CityEntity
import com.kelly.core.database.entities.CoordEntity
import com.kelly.core.database.entities.ItemsEntity
import com.kelly.core.database.entities.MainEntity
import com.kelly.core.database.entities.RainEntity
import com.kelly.core.database.entities.SysEntity
import com.kelly.core.database.entities.WeatherEntity
import com.kelly.core.database.entities.WindEntity
import java.lang.reflect.Type

class MyWeatherConverter {

    private inline fun <reified T> getTypeToken(): Type {
        return object : TypeToken<T>() {}.type
    }

    @TypeConverter
    fun fromCity(value: CityEntity?): String {
        return value.toJson()
    }

    @TypeConverter
    fun toCity(data: String?): CityEntity? {
        return data.fromJson<CityEntity>()
    }

    @TypeConverter
    fun fromCoord(value: CoordEntity?): String {
        return value.toJson()
    }

    @TypeConverter
    fun toCoord(data: String?): CoordEntity? {
        return data.fromJson<CoordEntity>()
    }

    @TypeConverter
    fun fromMain(value: MainEntity?): String {
        return value.toJson()
    }

    @TypeConverter
    fun toMain(data: String?): MainEntity? {
        return data.fromJson<MainEntity>()
    }

    @TypeConverter
    fun fromWeather(value: WeatherEntity?): String {
        return value.toJson()
    }

    @TypeConverter
    fun toWeather(data: String?): WeatherEntity? {
        return data.fromJson<WeatherEntity>()
    }

    @TypeConverter
    fun fromWind(value: WindEntity?): String {
        return value.toJson()
    }

    @TypeConverter
    fun toWind(data: String?): WindEntity? {
        return data.fromJson<WindEntity>()
    }

    @TypeConverter
    fun fromSys(value: SysEntity?): String {
        return value.toJson()
    }

    @TypeConverter
    fun toSys(data: String?): SysEntity? {
        return data.fromJson<SysEntity>()
    }

    @TypeConverter
    fun fromRain(value: RainEntity?): String {
        return value.toJson()
    }

    @TypeConverter
    fun toRain(data: String?): RainEntity? {
        return data.fromJson<RainEntity>()
    }

    @TypeConverter
    fun fromItems(value: List<ItemsEntity>?): String {
        return value.toJson()
    }

    @TypeConverter
    fun toItems(data: String?): List<ItemsEntity>? {
        return Gson().fromJson(data, getTypeToken<List<ItemsEntity>>())
    }
}
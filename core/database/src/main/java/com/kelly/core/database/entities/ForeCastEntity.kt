package com.kelly.core.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ForeCastEntity(
    @PrimaryKey
    val cityId: Int,
    val city: CityEntity?,
    val cnt: Int?,
    val cod: String?,
    val list: List<ItemsEntity>?,
    val message: Int?
)

data class CityEntity(
    val coord: CoordEntity?,
    val country: String?,
    val id: Int?,
    val name: String?,
    val population: Int?,
    val sunrise: Int?,
    val sunset: Int?,
    val timezone: Int?
)

data class ItemsEntity(
    val clouds: CloudsEntity?,
    val dt: Int?,
    val dt_txt: String?,
    val main: MainEntity?,
    val pop: Double?,
    val rain: RainEntity?,
    val sys: SysEntity?,
    val visibility: Int?,
    val weather: List<WeatherEntity>?,
    val wind: WindEntity?
)

data class MainEntity(
    val feels_like: Double?,
    val grnd_level: Int?,
    val humidity: Int?,
    val pressure: Int?,
    val sea_level: Int?,
    val temp: Double?,
    val temp_kf: Double?,
    val temp_max: Double?,
    val temp_min: Double?
)

data class RainEntity(
    val threeHours: Double?
)

data class SysEntity(
    val pod: String?
)

data class WeatherEntity(
    val description: String?,
    val icon: String?,
    val id: Int?,
    val main: String?
)

data class WindEntity(
    val deg: Int?,
    val gust: Double?,
    val speed: Double?
)

data class CoordEntity(
    val lat: Double?,
    val lon: Double?
)

data class CloudsEntity(
    val all: Int?
)

package com.kelly.forecast.data.mapper

import com.kelly.core.common.model.Favorites
import com.kelly.core.common.model.FavoritesCity
import com.kelly.core.common.model.FavoritesCoord
import com.kelly.core.database.entities.CityEntity
import com.kelly.core.database.entities.CloudsEntity
import com.kelly.core.database.entities.CoordEntity
import com.kelly.core.database.entities.ForeCastEntity
import com.kelly.core.database.entities.ItemsEntity
import com.kelly.core.database.entities.MainEntity
import com.kelly.core.database.entities.RainEntity
import com.kelly.core.database.entities.SysEntity
import com.kelly.core.database.entities.WeatherEntity
import com.kelly.core.database.entities.WindEntity
import com.kelly.forecast.data.model.City
import com.kelly.forecast.data.model.Clouds
import com.kelly.forecast.data.model.Coord
import com.kelly.forecast.data.model.ForeCastResponse
import com.kelly.forecast.data.model.Items
import com.kelly.forecast.data.model.Main
import com.kelly.forecast.data.model.Rain
import com.kelly.forecast.data.model.Sys
import com.kelly.forecast.data.model.Weather
import com.kelly.forecast.data.model.Wind

val ForeCastResponse.toForeCastEntity
    get() = ForeCastEntity(
        cityId = city?.id ?: 0,
        city = city?.toCityEntity,
        cnt = cnt,
        cod = cod,
        list = list?.map { it.toItemsEntity },
        message = message
    )

val City.toCityEntity
    get() = CityEntity(
        coord = coord?.toCoordEntity,
        country = country,
        id = id,
        name = name,
        population = population,
        sunrise = sunrise,
        sunset = sunset,
        timezone = timezone
    )

val Coord.toCoordEntity
    get() = CoordEntity(
        lat = lat,
        lon = lon
    )

val Items.toItemsEntity
    get() = ItemsEntity(
        clouds = clouds?.toCloudsEntity,
        dt = dt,
        dt_txt = dt_txt,
        main = main?.toMainEntity,
        pop = pop,
        rain = rain?.toRainEntity,
        sys = sys?.toSysEntity,
        visibility = visibility,
        weather = weather?.map { it.toWeatherEntity },
        wind = wind?.toWindEntity
    )

val Clouds.toCloudsEntity
    get() = CloudsEntity(all = all)

val Main.toMainEntity
    get() = MainEntity(
        feels_like = feels_like,
        grnd_level = grnd_level,
        humidity = humidity,
        pressure = pressure,
        sea_level = sea_level,
        temp = temp,
        temp_kf = temp_kf,
        temp_max = temp_max,
        temp_min = temp_min
    )

val Rain.toRainEntity
    get() = RainEntity(threeHours = threeHours)

val Sys.toSysEntity
    get() = SysEntity(pod = pod)

val Weather.toWeatherEntity
    get() = WeatherEntity(
        description = description,
        icon = icon,
        id = id,
        main = main
    )

val Wind.toWindEntity
    get() = WindEntity(
        deg = deg,
        gust = gust,
        speed = speed
    )


val ForeCastEntity.toForeCastResponse
    get() = ForeCastResponse(
        city = city?.toCity,
        cnt = cnt,
        cod = cod,
        list = list?.map { it.toItems },
        message = message
    )

val CityEntity.toCity
    get() = City(
        coord = coord?.toCoord,
        country = country,
        id = id,
        name = name,
        population = population,
        sunrise = sunrise,
        sunset = sunset,
        timezone = timezone
    )

val CoordEntity.toCoord
    get() = Coord(
        lat = lat,
        lon = lon
    )

val ItemsEntity.toItems
    get() = Items(
        clouds = clouds?.toClouds,
        dt = dt,
        dt_txt = dt_txt,
        main = main?.toMain,
        pop = pop,
        rain = rain?.toRain,
        sys = sys?.toSys,
        visibility = visibility,
        weather = weather?.map { it.toWeather },
        wind = wind?.toWind
    )

val CloudsEntity.toClouds
    get() = Clouds(all = all)

val MainEntity.toMain
    get() = Main(
        feels_like = feels_like,
        grnd_level = grnd_level,
        humidity = humidity,
        pressure = pressure,
        sea_level = sea_level,
        temp = temp,
        temp_kf = temp_kf,
        temp_max = temp_max,
        temp_min = temp_min
    )

val RainEntity.toRain
    get() = Rain(threeHours = threeHours)

val SysEntity.toSys
    get() = Sys(pod = pod)

val WeatherEntity.toWeather
    get() = Weather(
        description = description,
        icon = icon,
        id = id,
        main = main
    )

val WindEntity.toWind
    get() = Wind(
        deg = deg,
        gust = gust,
        speed = speed
    )

val ForeCastResponse.toFavorites
    get() = Favorites(
        cityId = city?.id ?: 0,
        city = city?.toFavoritesCity,
        cnt = cnt,
        cod = cod,
        message = message
    )

val City.toFavoritesCity
    get() = FavoritesCity(
        coord = coord?.toFavoritesCoord,
        country = country,
        id = id,
        name = name,
        population = population,
        sunrise = sunrise,
        sunset = sunset,
        timezone = timezone
    )

val Coord.toFavoritesCoord
    get() = FavoritesCoord(
        lat = lat,
        lon = lon
    )
package com.kelly.favorites.data.mapper

import com.kelly.core.common.model.Favorites
import com.kelly.core.common.model.FavoritesCity
import com.kelly.core.common.model.FavoritesCoord
import com.kelly.core.database.entities.CityEntity
import com.kelly.core.database.entities.CoordEntity
import com.kelly.core.database.entities.FavoritesEntity

val Favorites.toFavoritesEntity
    get() = FavoritesEntity(
        cityId = cityId,
        city = city?.toCityEntity,
        cnt = cnt,
        cod = cod,
        message = message
    )

val FavoritesCity.toCityEntity
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

val FavoritesCoord.toCoordEntity
    get() = CoordEntity(
        lat = lat,
        lon = lon
    )

val FavoritesEntity.toFavorites
    get() = Favorites(
        cityId = cityId,
        city = city?.toFavoritesCity,
        cnt = cnt,
        cod = cod,
        message = message
    )

val CityEntity.toFavoritesCity
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

val CoordEntity.toFavoritesCoord
    get() = FavoritesCoord(
        lat = lat,
        lon = lon
    )

package com.kelly.core.common.model

data class Favorites(
    val cityId: Int,
    val city: FavoritesCity?,
    val cnt: Int?,
    val cod: String?,
    val message: Int?
)

data class FavoritesCity(
    val coord: FavoritesCoord?,
    val country: String?,
    val id: Int?,
    val name: String?,
    val population: Int?,
    val sunrise: Int?,
    val sunset: Int?,
    val timezone: Int?
)

data class FavoritesCoord(
    val lat: Double?,
    val lon: Double?
)

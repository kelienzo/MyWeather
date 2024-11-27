package com.kelly.core.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val cityId: Int,
    val city: CityEntity?,
    val cnt: Int?,
    val cod: String?,
    val message: Int?
)

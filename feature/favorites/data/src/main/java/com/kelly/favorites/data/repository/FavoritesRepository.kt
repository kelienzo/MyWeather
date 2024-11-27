package com.kelly.favorites.data.repository

import com.kelly.core.common.model.Favorites
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {

    fun getAllFavorites(): Flow<List<Favorites>>

    fun getSingleFavorite(cityId: Int): Flow<Favorites?>

    suspend fun insertFavorites(favorites: Favorites)

    suspend fun deleteFavoriteByCityId(cityId: Int)

    suspend fun deleteAllFavorites()
}
package com.kelly.favorites.data.repository

import com.kelly.core.common.model.Favorites
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FavoritesRepositoryImplTest : FavoritesRepository {

    private val favoritesList = mutableListOf<Favorites>()

    override fun getAllFavorites(): Flow<List<Favorites>> {
        return flowOf(favoritesList)
    }

    override fun getSingleFavorite(cityId: Int): Flow<Favorites?> {
        return flowOf(favoritesList.find { it.cityId == cityId })
    }

    override suspend fun insertFavorites(favorites: Favorites) {
        favoritesList.add(favorites)
    }

    override suspend fun deleteFavoriteByCityId(cityId: Int) {
        val itemToRemove = favoritesList.find { it.cityId == cityId }
        favoritesList.remove(itemToRemove)
    }

    override suspend fun deleteAllFavorites() {
        favoritesList.clear()
    }
}
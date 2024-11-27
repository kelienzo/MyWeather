package com.kelly.favorites.data.repository

import com.kelly.core.common.model.Favorites
import com.kelly.core.database.MyWeatherDB
import com.kelly.favorites.data.mapper.toFavorites
import com.kelly.favorites.data.mapper.toFavoritesEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val myWeatherDB: MyWeatherDB
) : FavoritesRepository {
    override fun getAllFavorites(): Flow<List<Favorites>> {
        return myWeatherDB.favoritesDao.getAllFavorites().map { it.map { fav -> fav.toFavorites } }
    }

    override fun getSingleFavorite(cityId: Int): Flow<Favorites?> {
        return myWeatherDB.favoritesDao.getSingleFavorite(cityId).map { it?.toFavorites }
    }

    override suspend fun insertFavorites(favorites: Favorites) {
        myWeatherDB.favoritesDao.insertFavorites(favorites.toFavoritesEntity)
    }

    override suspend fun deleteFavoriteByCityId(cityId: Int) {
        myWeatherDB.favoritesDao.deleteFavoriteByCityId(cityId)
    }

    override suspend fun deleteAllFavorites() {
        myWeatherDB.favoritesDao.deleteAllFavorites()
    }
}
package com.kelly.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.kelly.core.database.entities.FavoritesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {

    @Query("SELECT * FROM FavoritesEntity ORDER BY id DESC")
    fun getAllFavorites(): Flow<List<FavoritesEntity>>

    @Query("SELECT * FROM FavoritesEntity WHERE cityId = :cityId")
    fun getSingleFavorite(cityId: Int): Flow<FavoritesEntity?>

    @Upsert
    suspend fun insertFavorites(favoritesEntity: FavoritesEntity)

    @Query("DELETE FROM FavoritesEntity WHERE cityId = :cityId")
    suspend fun deleteFavoriteByCityId(cityId: Int)

    @Query("DELETE FROM FavoritesEntity")
    suspend fun deleteAllFavorites()
}
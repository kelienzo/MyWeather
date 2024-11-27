package com.kelly.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.kelly.core.database.entities.ForeCastEntity

@Dao
interface ForeCastDao {

    @Query("SELECT * FROM ForeCastEntity WHERE cityId = :cityId")
    suspend fun getForeCast(cityId: Int): ForeCastEntity?

    @Upsert
    suspend fun insertForeCast(foreCastEntity: ForeCastEntity)

    @Query("DELETE FROM ForeCastEntity WHERE cityId = :cityId")
    suspend fun deleteForeCast(cityId: Int)
}
package com.kelly.favorites.data.repository

import com.kelly.core.common.model.Favorites
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class FakeFavoritesRepositoryImplTest {

    private lateinit var favoritesRepository: FavoritesRepository

    @Before
    fun setUp() {
        favoritesRepository = FavoritesRepositoryImplTest()
        val favoritesToInsert = mutableListOf<Favorites>()
        (0..10).map {
            favoritesToInsert.add(
                Favorites(
                    cityId = it,
                    city = null,
                    cnt = 21,
                    cod = "",
                    message = 21
                )
            )
        }
        runTest {
            favoritesToInsert.forEach { favoritesRepository.insertFavorites(it) }
        }
    }

    @Test
    fun get_favorites_from_db_should_not_be_empty() = runTest {
        val favorites = favoritesRepository.getAllFavorites().first()
        assertEquals(true, favorites.isNotEmpty())
    }

    @Test
    fun delete_single_favorites_from_db_by_city_id() = runTest {
        favoritesRepository.deleteFavoriteByCityId(7)
        val recentlyDeletedFavorites = favoritesRepository.getSingleFavorite(cityId = 7).first()
        assertEquals(null, recentlyDeletedFavorites)
    }

    @Test
    fun delete_all_favorites_from_db_id() = runTest {
        favoritesRepository.deleteAllFavorites()
        val allFavorites = favoritesRepository.getAllFavorites().first()
        assertEquals(true, allFavorites.isEmpty())
    }
}
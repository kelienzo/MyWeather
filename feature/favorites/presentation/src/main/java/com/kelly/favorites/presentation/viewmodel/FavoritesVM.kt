package com.kelly.favorites.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelly.core.common.model.Favorites
import com.kelly.favorites.data.repository.FavoritesRepository
import com.kelly.favorites.presentation.ui.FavoritesEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesVM @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val _favoritesUiState = MutableStateFlow(emptyList<Favorites>())
    val favoritesUiState = _favoritesUiState.onStart { getAllFavorites() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onEvent(event: FavoritesEvent) {
        when (event) {
            is FavoritesEvent.OnDeleteFavorite -> viewModelScope.launch {
                deleteFavorite(event.cityId)
            }

            else -> Unit
        }
    }

    private fun getAllFavorites() {
        favoritesRepository.getAllFavorites()
            .onEach {
                _favoritesUiState.value = it
            }.launchIn(viewModelScope)
    }

    private suspend fun deleteFavorite(cityId: Int) {
        favoritesRepository.deleteFavoriteByCityId(cityId = cityId)
    }

    private suspend fun deleteAllFavorites() {
        favoritesRepository.deleteAllFavorites()
    }
}
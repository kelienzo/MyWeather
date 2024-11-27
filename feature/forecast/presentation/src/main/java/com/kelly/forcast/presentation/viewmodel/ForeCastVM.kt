package com.kelly.forcast.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelly.core.common.ApiResult
import com.kelly.core.common.model.Favorites
import com.kelly.favorites.data.repository.FavoritesRepository
import com.kelly.forcast.presentation.ui.ForeCastEvent
import com.kelly.forcast.presentation.viewmodel.state.ForeCastUiState
import com.kelly.forecast.data.model.ForeCastResponse
import com.kelly.forecast.data.repository.ForeCastRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForeCastVM @Inject constructor(
    private val foreCastRepository: ForeCastRepository,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val _foreCastUiState = MutableStateFlow(ForeCastUiState())
    val foreCastUiState = _foreCastUiState.asStateFlow()

    private val _favorites = MutableStateFlow(emptyList<Favorites>())
    val favorites = _favorites.onStart { getFavorites() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onEvent(event: ForeCastEvent) {
        when (event) {
            is ForeCastEvent.OnAddToFavorites -> viewModelScope.launch {
                addToFavorites(event.favorites)
            }

            is ForeCastEvent.OnRemoveFavorites -> viewModelScope.launch {
                removeFromFavorites(event.cityId)
            }

            is ForeCastEvent.OnGetForeCast -> getForeCast(event.cityId)
            else -> Unit
        }
    }

    private fun getForeCast(cityId: Int) {
        foreCastRepository.getForeCast(cityId = cityId)
            .onEach { result ->
                when (result) {
                    is ApiResult.Error -> updateForCastUiState(
                        errorMessage = null,
                        response = result.data
                    )

                    is ApiResult.Loading -> updateForCastUiState(
                        isLoading = true,
                        response = result.data
                    )

                    is ApiResult.Success -> updateForCastUiState(response = result.data)
                }
            }.launchIn(viewModelScope)
    }

    private fun getFavorites() {
        favoritesRepository.getAllFavorites()
            .onEach {
                _favorites.value = it
            }.launchIn(viewModelScope)
    }

    private suspend fun addToFavorites(favorites: Favorites) {
        favoritesRepository.insertFavorites(favorites)
    }

    private suspend fun removeFromFavorites(cityId: Int) {
        favoritesRepository.deleteFavoriteByCityId(cityId)
    }

    private fun updateForCastUiState(
        isLoading: Boolean = false,
        errorMessage: String? = null,
        response: ForeCastResponse? = null
    ) {
        _foreCastUiState.value = ForeCastUiState(
            isLoading, errorMessage, response
        )
    }
}
package com.kelly.search.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelly.search.data.model.SearchResponse
import com.kelly.search.data.repository.SearchCityRepository
import com.kelly.search.presentation.ui.SearchEvent
import com.kelly.search.presentation.viewmodel.state.SearchUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class SearchVM @Inject constructor(
    private val searchCityRepository: SearchCityRepository
) : ViewModel() {

    private val _searchUiState = MutableStateFlow(SearchUiState())
    val searchUiState = _searchUiState.asStateFlow()


    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnSearch -> searchCity(event.searchText)
            else -> Unit
        }
    }

    private fun searchCity(search: String) {
        searchCityRepository.searchCity(search)
            .onStart {
                updateSearchUiState(isLoading = true)
            }.onEach { result ->
                result.onSuccess {
                    updateSearchUiState(response = it)
                }.onFailure {
                    updateSearchUiState(errorMessage = it.message)
                }
            }.launchIn(viewModelScope)
    }

    private fun updateSearchUiState(
        isLoading: Boolean = false,
        errorMessage: String? = null,
        response: SearchResponse? = null
    ) {
        _searchUiState.value = SearchUiState(isLoading, errorMessage, response)
    }
}
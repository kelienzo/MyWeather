package com.kelly.search.presentation.viewmodel.state

import com.kelly.search.data.model.SearchResponse

data class SearchUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val response: SearchResponse? = null
)

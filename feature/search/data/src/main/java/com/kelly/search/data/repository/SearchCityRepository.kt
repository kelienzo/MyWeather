package com.kelly.search.data.repository

import com.kelly.search.data.model.SearchResponse
import kotlinx.coroutines.flow.Flow

interface SearchCityRepository {

    fun searchCity(search: String): Flow<Result<SearchResponse>>
}
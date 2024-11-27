package com.kelly.search.data.repository

import com.kelly.core.common.model.ErrorModel
import com.kelly.core.common.utils.fromJson
import com.kelly.search.data.model.SearchResponse
import com.kelly.search.data.service.SearchCityApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SearchCityRepositoryImpl @Inject constructor(
    private val searchCityApiService: SearchCityApiService,
    private val coroutineDispatcher: CoroutineDispatcher,
    private val apiKey: String
) : SearchCityRepository {

    override fun searchCity(
        search: String
    ): Flow<Result<SearchResponse>> = flow {
        val response = searchCityApiService.searchCity(search = search, apiKey = apiKey)

        if (response.isSuccessful) {
            response.body()?.let { res ->
                emit(Result.success(res))
            } ?: emit(Result.failure(Throwable("An error occurred.")))
        } else {
            val error = response.errorBody()?.string().fromJson<ErrorModel>()
            emit(Result.failure(Throwable(error?.message ?: "An error occurred.")))
        }
    }.catch {
        it.printStackTrace()
        emit(Result.failure(Throwable("An exception occurred.")))
    }.flowOn(coroutineDispatcher)
}
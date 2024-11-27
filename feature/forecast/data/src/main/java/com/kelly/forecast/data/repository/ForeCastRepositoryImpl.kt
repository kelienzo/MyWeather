package com.kelly.forecast.data.repository

import androidx.room.withTransaction
import com.kelly.core.common.ApiResult
import com.kelly.core.common.model.ErrorModel
import com.kelly.core.common.utils.fromJson
import com.kelly.core.database.MyWeatherDB
import com.kelly.forecast.data.mapper.toForeCastEntity
import com.kelly.forecast.data.mapper.toForeCastResponse
import com.kelly.forecast.data.model.ForeCastResponse
import com.kelly.forecast.data.service.ForeCastApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ForeCastRepositoryImpl @Inject constructor(
    private val myWeatherDB: MyWeatherDB,
    private val foreCastApiService: ForeCastApiService,
    private val coroutineDispatcher: CoroutineDispatcher,
    private val apiKey: String
) : ForeCastRepository {

    override fun getForeCast(
        cityId: Int
    ): Flow<ApiResult<ForeCastResponse>> = flow<ApiResult<ForeCastResponse>> {
        emit(ApiResult.Loading())

        val forCastResultFromDB = myWeatherDB.foreCastDao.getForeCast(cityId)
        emit(ApiResult.Loading(data = forCastResultFromDB?.toForeCastResponse))

        try {
            val response = foreCastApiService.getForCast(
                cityId = cityId,
                apiKey = apiKey
            )
            if (response.isSuccessful) {
                response.body()?.let { res ->
                    myWeatherDB.run {
                        withTransaction {
                            foreCastDao.run {
                                deleteForeCast(res.city?.id ?: 0)
                                insertForeCast(res.toForeCastEntity)
                            }
                        }
                    }
                } ?: emit(
                    ApiResult.Error(
                        "An error occurred.",
                        data = forCastResultFromDB?.toForeCastResponse
                    )
                )
            } else {
                val error = response.errorBody()?.string().fromJson<ErrorModel>()
                emit(
                    ApiResult.Error(
                        error?.message ?: "An error occurred.",
                        data = forCastResultFromDB?.toForeCastResponse
                    )
                )
            }
        } catch (t: Throwable) {
            t.printStackTrace()
            emit(
                ApiResult.Error(
                    "An exception occurred",
                    data = forCastResultFromDB?.toForeCastResponse
                )
            )
        }

        val newForCastResult = myWeatherDB.foreCastDao.getForeCast(cityId)
        emit(ApiResult.Success(data = newForCastResult?.toForeCastResponse))
    }.flowOn(coroutineDispatcher)
}
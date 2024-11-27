package com.kelly.forecast.data.repository

import com.kelly.core.common.ApiResult
import com.kelly.forecast.data.model.ForeCastResponse
import kotlinx.coroutines.flow.Flow

interface ForeCastRepository {

    fun getForeCast(
        cityId: Int
    ): Flow<ApiResult<ForeCastResponse>>
}
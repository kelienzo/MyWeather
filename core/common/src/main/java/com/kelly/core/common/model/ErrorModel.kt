package com.kelly.core.common.model

data class ErrorModel(
    val cod: Int?,
    val message: String?,
    val parameters: List<String>?
)
package com.kelly.core.common.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Routes {

    @Serializable
    data object HomeNavScreen : Routes

    @Serializable
    data object SearchNavScreen : Routes

    @Serializable
    data object FavoritesNavScreen : Routes

    @Serializable
    data class ForeCastNavScreen(val cityId: Int) : Routes
}
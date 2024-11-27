package com.kelly.core.common.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

interface NavigationGraph {

    fun initializeGraph(
        navHostController: NavHostController,
        navGraphBuilder: NavGraphBuilder
    )
}
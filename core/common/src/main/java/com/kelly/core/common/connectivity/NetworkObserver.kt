package com.kelly.core.common.connectivity

import kotlinx.coroutines.flow.StateFlow

interface NetworkObserver {

    val isConnected: StateFlow<Boolean>

    fun initialize()
}
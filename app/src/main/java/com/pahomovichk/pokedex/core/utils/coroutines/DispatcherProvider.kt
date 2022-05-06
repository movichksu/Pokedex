package com.pahomovichk.pokedex.core.utils.coroutines

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {
    val default: CoroutineDispatcher
    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
}
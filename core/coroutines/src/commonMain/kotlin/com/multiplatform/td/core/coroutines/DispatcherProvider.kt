package com.multiplatform.td.core.coroutines

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {

    val ui: CoroutineDispatcher

    val io: CoroutineDispatcher

    val computation: CoroutineDispatcher
}

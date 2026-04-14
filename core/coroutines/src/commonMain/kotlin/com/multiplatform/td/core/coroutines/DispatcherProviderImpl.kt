package com.multiplatform.td.core.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

internal class DispatcherProviderImpl(
    private val mainProvider: () -> CoroutineDispatcher = { Dispatchers.Main },
    private val ioProvider: () -> CoroutineDispatcher = { Dispatchers.IO },
    private val computationProvider: () -> CoroutineDispatcher = { Dispatchers.Default },
) : DispatcherProvider {

    override val ui: CoroutineDispatcher
        get() = mainProvider()

    override val io: CoroutineDispatcher
        get() = ioProvider()

    override val computation: CoroutineDispatcher
        get() = computationProvider()
}

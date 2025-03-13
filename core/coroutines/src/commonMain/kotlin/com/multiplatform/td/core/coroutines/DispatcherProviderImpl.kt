package com.multiplatform.td.core.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

internal class DispatcherProviderImpl : DispatcherProvider {

    override val ui: CoroutineDispatcher
        get() = Dispatchers.Main

    override val io: CoroutineDispatcher
        get() = Dispatchers.IO

    override val computation: CoroutineDispatcher
        get() = Dispatchers.Default
}

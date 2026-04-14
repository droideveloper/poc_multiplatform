@file:OptIn(ExperimentalCoroutinesApi::class)

package com.multiplatform.td.core.coroutines

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlin.test.Test
import kotlin.test.assertEquals

internal class DispatcherProviderImplTest {

    private val dispatcher = UnconfinedTestDispatcher()

    @Test
    fun `given ui dispatcher will equal to provided one`() {
        val dispatcherProvider: DispatcherProvider = DispatcherProviderImpl(
            mainProvider = { dispatcher },
        )

        val actual = dispatcherProvider.ui
        assertEquals(dispatcher, actual)
    }

    @Test
    fun `given io dispatcher will equal to provided one`() {
        val dispatcherProvider: DispatcherProvider = DispatcherProviderImpl(
            ioProvider = { dispatcher },
        )

        val actual = dispatcherProvider.io
        assertEquals(dispatcher, actual)
    }

    @Test
    fun `given computation dispatcher will equal to provided one`() {
        val dispatcherProvider: DispatcherProvider = DispatcherProviderImpl(
            computationProvider = { dispatcher },
        )

        val actual = dispatcherProvider.computation
        assertEquals(dispatcher, actual)
    }
}

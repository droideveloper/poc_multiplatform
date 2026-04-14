package com.multiplatform.td.core.coroutines

import app.cash.turbine.Event
import app.cash.turbine.test
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Instant

internal class FlowExtensionsTest {

    @Test
    fun `given throttle will emit before that period elapsed`() = runTest {
        val instant = Instant.fromEpochMilliseconds(1000)

        flowOf(1, 2)
            .throttle(200.milliseconds, clockProvider = { instant })
            .test {
                assertEquals(1, awaitItem())
                delay(200)
                assertEquals(Event.Complete, awaitEvent())
                cancelAndConsumeRemainingEvents()
            }
    }

    @Test
    fun `given throttle will emit after that period elapsed`() = runTest {
        var instant = Instant.fromEpochMilliseconds(1000)
        val sharedFlow = MutableSharedFlow<Int>()

        sharedFlow
            .throttle(200.milliseconds, clockProvider = { instant })
            .test {
                sharedFlow.emit(1)
                sharedFlow.emit(2)
                assertEquals(1, awaitItem())
                delay(200)
                instant = instant.plus(200.milliseconds)
                sharedFlow.emit(3)
                assertEquals(3, awaitItem())
                cancelAndConsumeRemainingEvents()
            }
    }
}

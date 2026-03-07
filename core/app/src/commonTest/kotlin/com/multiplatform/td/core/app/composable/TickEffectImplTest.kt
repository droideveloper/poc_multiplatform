package com.multiplatform.td.core.app.composable

import com.multiplatform.td.core.coroutines.DispatcherProvider
import com.multiplatform.td.core.testing.AbstractDispatcherTest
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.time.Duration.Companion.milliseconds

internal class TickEffectImplTest : AbstractDispatcherTest() {

    private val dispatcherProvider = mock<DispatcherProvider>().apply {
        every { computation } returns dispatcher
    }

    @Test
    fun `given tick start will continue until disposed`() = runTest {
        val completable = CompletableDeferred(Unit)
        val onTick: () -> Unit = {
            completable.complete(Unit)
        }

        val tickEffect: TickEffect = TickEffectImpl(dispatcherProvider, onTick, 10.milliseconds)
        tickEffect.onStart()
        launch {
            completable.await()
        }

        tickEffect.onStop()
    }
}

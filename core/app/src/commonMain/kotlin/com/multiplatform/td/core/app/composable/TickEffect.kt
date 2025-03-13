package com.multiplatform.td.core.app.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import com.multiplatform.td.core.coroutines.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@Composable
fun TickEffect(
    onTick: () -> Unit,
    duration: Duration = 1.seconds,
    tickEffect: TickEffect = rememberTick(onTick, duration),
) {
    DisposableEffect(onTick) {
        tickEffect.onStart()
        onDispose { tickEffect.onStop() }
    }
}

@Composable
internal fun rememberTick(
    onTick: () -> Unit,
    duration: Duration,
): TickEffect {
    val component = LocalAppComponent.current
    val dispatcherProvider = remember { component.dispatcherProvider }
    return remember(onTick, duration) {
        TickEffectImpl(dispatcherProvider, onTick, duration)
    }
}

interface TickEffect {
    fun onStart()
    fun onStop()
    fun clear()
}

internal class TickEffectImpl(
    dispatcherProvider: DispatcherProvider,
    private val onTick: () -> Unit,
    private val duration: Duration,
) : TickEffect {
    private val scope by lazy {
        CoroutineScope(SupervisorJob() + dispatcherProvider.computation)
    }

    private var timer: Job? = null

    override fun onStart() {
        clear()
        timer = scope.launch {
            while (true) {
                delay(duration)
                onTick()
            }
        }
    }

    override fun onStop() = clear()

    override fun clear() {
        timer?.cancel()
        timer = null
    }
}

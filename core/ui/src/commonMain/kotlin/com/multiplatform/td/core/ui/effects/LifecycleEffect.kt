package com.multiplatform.td.core.ui.effects

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun OnScreenCreate(
    onCreate: () -> Unit,
) {
    OnLifecycleEffect(onCreate = onCreate)
}

@Composable
fun OnScreenStart(
    onStart: () -> Unit,
) {
    OnLifecycleEffect(onStart = onStart)
}

@Composable
fun OnScreenStop(
    onStop: () -> Unit,
) {
    OnLifecycleEffect(onStop = onStop)
}

@Composable
fun OnScreenResume(
    onResume: () -> Unit,
) {
    OnLifecycleEffect(onResume = onResume)
}

@Composable
fun OnScreenPause(
    onPause: () -> Unit,
) {
    OnLifecycleEffect(onPause = onPause)
}

@Composable
internal fun OnLifecycleEffect(
    onCreate    : () -> Unit = {},
    onStart     : () -> Unit = {},
    onResume    : () -> Unit = {},
    onPause     : () -> Unit = {},
    onStop      : () -> Unit = {},
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycle = remember { lifecycleOwner.lifecycle }

    DisposableEffect(Unit) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> onCreate()
                Lifecycle.Event.ON_START -> onStart()
                Lifecycle.Event.ON_RESUME -> onResume()
                Lifecycle.Event.ON_PAUSE -> onPause()
                Lifecycle.Event.ON_STOP -> onStop()
                else -> Unit
            }
        }
        lifecycle.addObserver(observer)

        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}

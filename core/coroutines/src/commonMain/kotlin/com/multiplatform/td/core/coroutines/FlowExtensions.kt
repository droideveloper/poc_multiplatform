@file:OptIn(ExperimentalTime::class)

package com.multiplatform.td.core.coroutines

import kotlin.time.Duration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

fun <T> Flow<T>.throttle(delay: Duration): Flow<T>  = flow {
    require(delay.isPositive())
    var previous: Instant? = null
    collect { value ->
        val current = Clock.System.now()
        when (val before = previous) {
            null -> {
                previous = current
                emit(value)
            }
            else -> if (current.minus(before) >= delay) {
                previous = current
                emit(value)
            }
        }
    }
}

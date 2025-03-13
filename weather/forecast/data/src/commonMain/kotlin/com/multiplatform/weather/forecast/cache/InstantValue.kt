@file:OptIn(ExperimentalTime::class)

package com.multiplatform.weather.forecast.cache

import com.multiplatform.weather.forecast.ForecastDto
import kotlinx.serialization.Serializable
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Serializable
internal data class InstantValue(
    val time: Instant,
    val value: ForecastDto,
) {

    companion object {
        fun now(value: ForecastDto) =
            InstantValue(
                value = value,
                time = Clock.System.now(),
            )
    }
}
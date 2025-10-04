@file:OptIn(ExperimentalTime::class)

package com.multiplatform.weather.forecast.cache

import com.multiplatform.weather.forecast.ForecastDto
import com.multiplatform.weather.forecast.LocalDateTimeProvider
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.serialization.Serializable
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Serializable
internal data class InstantValue(
    val time: Instant,
    val value: ForecastDto,
) {

    companion object {
        fun now(
            value: ForecastDto,
            localDateTimeProvider: LocalDateTimeProvider,
        ) =
            InstantValue(
                value = value,
                time = localDateTimeProvider().toInstant(
                    timeZone = TimeZone.UTC,
                ),
            )
    }
}

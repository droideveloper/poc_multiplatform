@file:OptIn(ExperimentalTime::class)

package com.multiplatform.weather.forecast

import com.multiplatform.td.core.environment.Environment
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

internal class LocalDateTimeProviderImpl(
    private val environment: Environment,
) : LocalDateTimeProvider {

    private val timeZone by lazy {
        when {
            environment.isMock -> TimeZone.UTC
            else -> TimeZone.currentSystemDefault()
        }
    }

    override fun invoke(): LocalDateTime =
        when {
            environment.isMock -> provideMock()
            else -> Clock.System.now()
        }
            .toLocalDateTime(
                timeZone = timeZone,
            )

    private fun provideMock(): Instant = Instant.parse("2025-10-04T05:00:00Z")
}

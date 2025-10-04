@file:OptIn(ExperimentalTime::class)

package com.multiplatform.weather.forecast

import com.multiplatform.td.core.environment.Environment
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import dev.mokkery.verify
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

internal class LocalDateTimeProviderImplTest {

    @Test
    fun `given mock than will provide time`() {
        val environment = mock<Environment> {
            every { isMock } returns true
        }

        val expected = Instant.parse("2025-10-04T05:00:00Z")
            .toLocalDateTime(timeZone = TimeZone.UTC)

        val localDateTimeProvider = LocalDateTimeProviderImpl(environment)

        val actual = localDateTimeProvider()
        assertEquals(expected, actual)

        verify { environment.isMock }
    }

    @Test
    fun `given other flavors than will provide system time`() {
        val environment = mock<Environment> {
            every { isMock } returns false
        }

        val localDateTimeProvider = LocalDateTimeProviderImpl(environment)

        val expected = Clock.System.now().toLocalDateTime(
            timeZone = TimeZone.UTC,
        )
        val actual = localDateTimeProvider()

        assertEquals(expected.year, actual.year)
        assertEquals(expected.month, actual.month)
        assertEquals(expected.day, actual.day)

        verify { environment.isMock }
    }
}

@file:OptIn(ExperimentalTime::class)

package com.multiplatform.todo.tasks

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime

@Composable
internal fun selectContainerColor(color: CategoryColor): Color =
    remember(color) { Color(color.value.toULong()) }

@Composable
internal fun selectLocalDate(date: LocalDate): String {
    val dateFormat = remember {
        LocalDate.Format {
            day(Padding.ZERO)
            char(' ')
            monthName(MonthNames.ENGLISH_FULL)
            char(' ')
            year()
        }
    }
    return dateFormat.format(date)
}

@Composable
internal fun selectLocalTime(time: LocalTime, duration: Duration): String {
    val timeFormat = remember {
        LocalTime.Format {
            hour(Padding.ZERO)
            char(':')
            minute(Padding.ZERO)
        }
    }
    val ms = time.toMillisecondOfDay() + duration.toInt(DurationUnit.MILLISECONDS)
    val next = LocalTime.fromMillisecondOfDay(ms)
    return timeFormat.format(time) + "-" + timeFormat.format(next)
}

@Composable
internal fun selectSecondaryTitle(
    // may be provide this outside
    dateTime: LocalDateTime = Clock.System.now().toLocalDateTime(
        timeZone = TimeZone.currentSystemDefault(),
    ),
): String {
    val dateFormat = remember {
        LocalDateTime.Format {
            dayOfWeek(DayOfWeekNames.ENGLISH_ABBREVIATED)
            char(',')
            day(Padding.NONE)
            char(' ')
            monthName(MonthNames.ENGLISH_ABBREVIATED)
            char(' ')
            year(Padding.NONE)
        }
    }
    return dateFormat.format(dateTime)
}

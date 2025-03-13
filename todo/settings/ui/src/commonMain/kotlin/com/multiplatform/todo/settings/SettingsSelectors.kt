@file:OptIn(ExperimentalTime::class)

package com.multiplatform.todo.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.multiplatform.td.core.ui.TdTheme
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Composable
internal fun selectSecondaryTitle(
    // may be provide this outside
    dateTime: LocalDateTime = Clock.System.now().toLocalDateTime(
        timeZone = TimeZone.currentSystemDefault()
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

@Composable
internal fun selectTitleColor(isEnabled: Boolean): Color = when {
    isEnabled -> TdTheme.colors.blacks.secondary
    else -> TdTheme.colors.greys.primary
}

@Composable
internal fun selectButtonColor(isEnabled: Boolean): Color = when {
    isEnabled -> TdTheme.colors.deepOranges.secondary
    else -> TdTheme.colors.greys.secondary
}

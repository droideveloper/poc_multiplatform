@file:OptIn(ExperimentalTime::class)

package com.multiplatform.todo.home.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource
import tdmultiplatform.todo.home.ui.generated.resources.Res
import tdmultiplatform.todo.home.ui.generated.resources.home_add_new_category_description
import tdmultiplatform.todo.home.ui.generated.resources.home_add_new_task_description
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Composable
internal fun selectNewButtonDescription(menuItem: MenuItem): String = when (menuItem) {
    is MenuItem.Settings -> stringResource(Res.string.home_add_new_category_description)
    else -> stringResource(Res.string.home_add_new_task_description)
}

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
@file:OptIn(ExperimentalTime::class)

package com.multiplatform.todo.calendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.debugInspectorInfo
import com.multiplatform.td.core.ui.TdTheme
import com.multiplatform.td.core.ui.extensions.ignoreHorizontalPadding
import com.multiplatform.todo.tasks.CategoryColor
import com.multiplatform.todo.tasks.Task
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime

// Duplicate from Task ui
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

// Duplicate from Task ui
@Composable
internal fun selectContainerColor(color: CategoryColor): Color =
    remember(color) { Color(color.value.toULong()) }

@Composable
internal fun selectDayName(day: LocalDate): String {
    val dateFormat = remember { LocalDate.Format { dayOfWeek(DayOfWeekNames.ENGLISH_ABBREVIATED) } }
    return dateFormat.format(day).trim()
}

@Composable
internal fun selectDayNumber(day: LocalDate): String {
    val dateFormat = remember { LocalDate.Format { day(Padding.ZERO) } }
    return dateFormat.format(day)
}

@Composable
internal fun selectDayNameColor(isSelected: Boolean): Color = when {
    isSelected -> TdTheme.colors.deepOranges.secondary
    else -> TdTheme.colors.blacks.light
}

@Composable
internal fun selectDayNumberColor(isSelected: Boolean): Color = when {
    isSelected -> TdTheme.colors.deepOranges.secondary
    else -> TdTheme.colors.blacks.primary
}

@Composable
internal fun selectSelectionIndicatorColor(isSelected: Boolean): Color = when {
    isSelected -> TdTheme.colors.deepOranges.secondary
    else -> Color.Transparent
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

@Composable
internal fun List<Task>.selectTaskComposable(
    onTaskClick: (Task) -> Unit,
): Map<Int, @Composable () -> Unit> =
    associateBy(
        keySelector = {
            // if spot is 24, pick as it is, else increment and pick
            when (val hour = it.dueDateTime.hour) {
                24 -> hour
                else -> hour + 1
            }
        },
        valueTransform = {
            {
                CalendarScheduleTask(
                    modifier = Modifier
                        .ignoreHorizontalPadding(TdTheme.dimens.standard16)
                        .layoutTaskOnTimeSlot(it.dueDateTime.minute),
                    task = it,
                    onTaskClick = onTaskClick,
                )
            }
        },
    )

@Composable
fun Modifier.layoutTaskOnTimeSlot(
    minute: Int,
): Modifier =
    composed(
        inspectorInfo =
        debugInspectorInfo {
            name = "layoutTaskOnTimeSlot"
            value = minute
        },
    ) {
        val height = LocalDensity.current.run { TdTheme.dimens.standard96.toPx() }
        // measure y of calendar item
        layout { measurable, constraints ->
            val measured = measurable.measure(constraints)
            val x = (measured.width / 4f).roundToInt()

            val ratio = (minute - 60) / 60f
            val deltaY = (ratio * height).roundToInt()
            val y = min(max(deltaY - measured.height / 2, -height.toInt()), 0)

            layout(measured.width, measured.height) {
                measured.place(x, y)
            }
        }
    }

@Composable
fun Modifier.layoutScheduleOnTimeSlot(
    minute: Int,
): Modifier =
    composed(
        inspectorInfo =
        debugInspectorInfo {
            name = "layoutScheduleOnTimeSlot"
            value = minute
        },
    ) {
        val height = LocalDensity.current.run { TdTheme.dimens.standard96.toPx() }
        // measure y of calendar item
        layout { measurable, constraints ->
            val measured = measurable.measure(constraints)
            val x = (measured.width / 6f).roundToInt()

            val ratio = (minute - 60) / 60f
            val deltaY = (ratio * height).roundToInt()
            val y = min(max(deltaY - measured.height / 2, -height.toInt()), 0)

            layout(measured.width, measured.height) {
                measured.place(x, y)
            }
        }
    }

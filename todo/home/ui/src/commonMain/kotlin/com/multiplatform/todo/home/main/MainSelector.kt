package com.multiplatform.todo.home.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.multiplatform.td.core.ui.TdTheme
import com.multiplatform.td.core.ui.extensions.ignoreHorizontalPadding
import com.multiplatform.todo.calendar.layoutScheduleOnTimeSlot
import com.multiplatform.todo.home.TodayScheduleTask
import com.multiplatform.todo.tasks.Category
import com.multiplatform.todo.tasks.CategoryColor
import com.multiplatform.todo.tasks.Task
import com.multiplatform.todo.tasks.TaskStatus
import com.multiplatform.todo.tasks.TaskSubTasks
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import kotlin.time.Duration
import kotlin.time.DurationUnit

@Composable
internal fun selectLocalDate(date: LocalDate): String {
    val dateFormat = remember { LocalDate.Format {
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
    val timeFormat = remember { LocalTime.Format {
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
internal fun selectContainerColor(color: CategoryColor): Color =
    remember(color) { Color(color.value.toULong()) }

@Composable
internal fun List<Task>.selectTaskComposable(
    onTaskClick: (Task) -> Unit,
    onCategoryClick : (Category) -> Unit,
): Map<Int, @Composable () -> Unit> =
    associateBy(
        keySelector =  {
            // if spot is 24, pick as it is, else increment and pick
            when (val hour = it.dueDateTime.hour) {
                24 -> hour
                else -> hour + 1
            }
        },
        valueTransform = {
            {
                // composable here
                TodayScheduleTask(
                    modifier = Modifier
                        .ignoreHorizontalPadding(TdTheme.dimens.standard16)
                        .layoutScheduleOnTimeSlot(it.dueDateTime.minute),
                    task = it,
                    onCategoryClick = onCategoryClick,
                    onTaskClick = onTaskClick,
                )
            }
        }
    )

internal fun TaskSubTasks.selectProgress(): Float {
    val left = tasks.count { it.status != TaskStatus.Done }
    val total = when (val size = tasks.size) {
        0 -> 1
        else -> size
    }
    return left / total.toFloat()
}

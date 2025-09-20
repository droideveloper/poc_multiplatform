package com.multiplatform.todo.calendar

import com.multiplatform.todo.tasks.Task
import kotlinx.datetime.LocalDate

internal sealed interface CalendarEvent {

    data object OnScreenViewed : CalendarEvent
    data object OnFilterClicked : CalendarEvent
    data class OnTaskClicked(
        val task: Task,
    ) : CalendarEvent

    data class OnDateClicked(
        val date: LocalDate,
    ) : CalendarEvent
}

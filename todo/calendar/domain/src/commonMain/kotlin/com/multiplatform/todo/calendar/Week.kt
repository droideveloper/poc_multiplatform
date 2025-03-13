package com.multiplatform.todo.calendar

import kotlinx.datetime.LocalDate

data class Week(
    val today: LocalDate,
    val days: List<LocalDate>,
) {

    companion object {
        val Default = Week(
            today = LocalDate(2025,3,4),
            days = emptyList(),
        )
    }
}

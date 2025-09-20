package com.multiplatform.todo.calendar.usecase

import com.multiplatform.todo.calendar.Week
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import me.tatarka.inject.annotations.Inject

@Inject
class GetWeekUseCase {

    suspend operator fun invoke(today: LocalDate): Result<Week> = runCatching {
        val days = (today.nextDays() + today.previousDays())
            .toList()
            .sortedBy { it.day }

        Week(
            today = today,
            days = days,
        )
    }
}

private fun LocalDate.tomorrow(): LocalDate =
    plus(1, DateTimeUnit.DAY)

private fun LocalDate.yesterday(): LocalDate =
    minus(1, DateTimeUnit.DAY)

private fun LocalDate.nextDays(): Set<LocalDate> = when (dayOfWeek) {
    DayOfWeek.SATURDAY -> setOf(this)
    else -> setOf(this) + tomorrow().nextDays()
}

private fun LocalDate.previousDays(): Set<LocalDate> = when (dayOfWeek) {
    DayOfWeek.SUNDAY -> setOf(this)
    else -> setOf(this) + yesterday().previousDays()
}

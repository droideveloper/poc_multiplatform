package com.multiplatform.weather.forecast

import kotlinx.datetime.LocalDate

data class Forecast(
    val weather: Map<LocalDate, List<Weather>>,
    val average: List<Average>,
) {

    companion object {
        val Defaults = Forecast(
            weather = emptyMap(),
            average = emptyList(),
        )
    }
}

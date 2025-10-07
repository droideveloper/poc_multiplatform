package com.multiplatform.weather.forecast

import kotlinx.datetime.LocalDateTime

interface LocalDateTimeProvider {

    operator fun invoke(): LocalDateTime
}

package com.multiplatform.weather.forecast

import com.multiplatform.td.core.kotlin.ValueObjectFactory
import kotlin.jvm.JvmInline

@JvmInline
value class WeatherCode internal constructor(val code: Int) {

    companion object : ValueObjectFactory<Int, WeatherCode, WeatherCodeException>() {
        override val initializer: (Int) -> WeatherCode = ::WeatherCode
        override fun isValid(input: Int): Boolean = input >= 0 || input < 100
        override fun getThrowable(input: Int): WeatherCodeException? = when {
            isValid(input) -> null
            else -> WeatherCodeException.Invalid
        }
    }
}

sealed class WeatherCodeException : IllegalArgumentException() {
    data object Invalid : WeatherCodeException()
}
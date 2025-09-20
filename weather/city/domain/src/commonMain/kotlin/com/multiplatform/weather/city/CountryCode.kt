package com.multiplatform.weather.city

import com.multiplatform.td.core.kotlin.ValueObjectFactory
import kotlin.jvm.JvmInline

@JvmInline
value class CountryCode internal constructor(val value: String) {

    companion object : ValueObjectFactory<String, CountryCode, CountryCodeException>() {
        override val initializer: (String) -> CountryCode = ::CountryCode
        override fun isValid(input: String): Boolean = input.isNotEmpty() && input.length == 2
        override fun getThrowable(input: String): CountryCodeException? = when {
            input.isEmpty() -> CountryCodeException.EmptyCountryCode
            input.length != 2 -> CountryCodeException.InvalidCountryCode
            else -> null
        }
    }
}

sealed class CountryCodeException : IllegalArgumentException() {

    data object InvalidCountryCode : CountryCodeException()
    data object EmptyCountryCode : CountryCodeException()
}

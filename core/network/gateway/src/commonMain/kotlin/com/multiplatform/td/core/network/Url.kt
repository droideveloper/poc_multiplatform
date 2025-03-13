package com.multiplatform.td.core.network

import com.multiplatform.td.core.kotlin.ValueObjectFactory
import kotlin.jvm.JvmInline

@JvmInline
value class Url internal constructor(val value: String) {

    companion object : ValueObjectFactory<String, Url, Companion.InvalidUrl>() {
        override val initializer: (String) -> Url = ::Url

        override fun isValid(input: String): Boolean =
            input.isNotEmpty() && (input.startsWith("https://") || input.startsWith("http://"))

        override fun getThrowable(input: String): InvalidUrl? = when {
            isValid(input) -> null
            else -> InvalidUrl(input)
        }

        data class InvalidUrl(val url: String): IllegalArgumentException()
    }
}

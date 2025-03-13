package com.multiplatform.td.core.network

import com.multiplatform.td.core.kotlin.ValueObjectFactory
import kotlin.jvm.JvmInline

@JvmInline
value class HttpErrorCode private constructor(val code: Int) {

    companion object : ValueObjectFactory<Int, HttpErrorCode, InvalidCode>() {
        override val initializer: (Int) -> HttpErrorCode = ::HttpErrorCode
        override fun isValid(input: Int): Boolean = input < 200 || input > 300
        override fun getThrowable(input: Int): InvalidCode? =
            when {
                isValid(input) -> null
                else -> InvalidCode()
            }
    }

    class InvalidCode: IllegalArgumentException()
}

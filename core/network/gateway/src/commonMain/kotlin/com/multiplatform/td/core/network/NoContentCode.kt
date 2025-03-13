package com.multiplatform.td.core.network

import com.multiplatform.td.core.kotlin.ValueObjectFactory
import kotlin.jvm.JvmInline

@JvmInline
value class NoContentCode private constructor(val code: Int) {

    companion object: ValueObjectFactory<Int, NoContentCode, InvalidErrorCode>() {
        override val initializer: (Int) -> NoContentCode = ::NoContentCode
        override fun isValid(input: Int): Boolean = input == 204 || input == 205
        override fun getThrowable(input: Int): InvalidErrorCode? =
            when {
                isValid(input) -> null
                else -> InvalidErrorCode()
            }
    }

    class InvalidErrorCode: IllegalArgumentException()
}

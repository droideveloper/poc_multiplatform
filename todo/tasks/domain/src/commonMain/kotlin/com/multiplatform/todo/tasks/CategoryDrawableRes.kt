package com.multiplatform.todo.tasks

import com.multiplatform.td.core.kotlin.ValueObjectFactory
import kotlin.jvm.JvmInline

@JvmInline
value class CategoryDrawableRes private constructor(val value: String) {

    companion object : ValueObjectFactory<String, CategoryDrawableRes, CategoryDrawableResException>() {
        override val initializer: (String) -> CategoryDrawableRes = ::CategoryDrawableRes
        override fun isValid(input: String): Boolean = input.isNotEmpty()
        override fun getThrowable(input: String): CategoryDrawableResException? =
            when {
                isValid(input) -> null
                else -> CategoryDrawableResException.Invalid(input)
            }
    }
}

sealed class CategoryDrawableResException : IllegalArgumentException() {

    data class Invalid(val res: String): CategoryDrawableResException()
}
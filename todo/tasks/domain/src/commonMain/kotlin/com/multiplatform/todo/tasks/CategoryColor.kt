package com.multiplatform.todo.tasks

import com.multiplatform.td.core.kotlin.ValueObjectFactory
import kotlin.jvm.JvmInline

@JvmInline
value class CategoryColor private constructor(val value: Long) {

    companion object : ValueObjectFactory<Long, CategoryColor, CategoryColorException>() {
        override val initializer: (Long) -> CategoryColor = ::CategoryColor
        override fun isValid(input: Long): Boolean = input > Long.MIN_VALUE && input < Long.MAX_VALUE
        override fun getThrowable(input: Long): CategoryColorException? =
            when {
                isValid(input) -> null
                else -> CategoryColorException.Invalid
            }
    }
}

sealed class CategoryColorException : IllegalArgumentException() {

    data object Invalid : CategoryColorException()
}

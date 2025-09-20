package com.multiplatform.td.core.app.error

import kotlin.reflect.KClass

sealed class CompositionContextException(message: String) : IllegalArgumentException(message) {

    data class NotFound(private val klass: KClass<*>) : CompositionContextException(
        message = "compositionLocalOf { ${klass.simpleName} } not provided, please provide value for it.",
    )
}

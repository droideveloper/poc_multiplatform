package com.multiplatform.td.core.injection.compiler.ext

import com.google.devtools.ksp.symbol.KSValueArgument
import com.google.devtools.ksp.symbol.KSValueParameter
import me.tatarka.inject.annotations.Assisted

internal fun KSValueParameter.requireName(): String = requireNotNull(name).asString()
internal fun KSValueParameter.isAssisted(): Boolean {
    return annotations.any { it.asShortName() == Assisted::class.simpleName }
}

internal fun KSValueArgument.requireName(): String = requireNotNull(name).asString()
package com.multiplatform.td.core.injection.compiler.ext

import com.google.devtools.ksp.symbol.KSAnnotation

internal fun KSAnnotation.asShortName(): String = shortName.asString()
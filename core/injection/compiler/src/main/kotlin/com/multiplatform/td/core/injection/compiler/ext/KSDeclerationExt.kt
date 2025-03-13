package com.multiplatform.td.core.injection.compiler.ext

import com.google.devtools.ksp.symbol.KSDeclaration
import com.google.devtools.ksp.symbol.KSFile

internal fun KSDeclaration.requireQualifierName(): String = requireNotNull(qualifiedName?.asString())
internal fun KSDeclaration.requireContainingFile(): KSFile = requireNotNull(containingFile)
internal fun KSDeclaration.packageName(): String = packageName.asString()
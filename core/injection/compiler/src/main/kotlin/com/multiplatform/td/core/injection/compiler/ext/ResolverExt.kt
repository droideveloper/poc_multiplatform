package com.multiplatform.td.core.injection.compiler.ext

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotated
import kotlin.reflect.KClass

internal fun Resolver.getSymbolsWithAnnotation(clazz: KClass<*>): Sequence<KSAnnotated> =
    getSymbolsWithAnnotation(clazz.requireQualifierName())
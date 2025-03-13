package com.multiplatform.td.core.injection.compiler.ext

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.plusParameter
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asClassName
import kotlin.reflect.KClass

internal fun KClass<*>.requireQualifierName(): String = requireNotNull(qualifiedName)
internal fun KClass<*>.requireParameterizedType(className: ClassName): ParameterizedTypeName =
    asClassName().plusParameter(className)
internal fun KClass<*>.requireParameterizedType(typeName: TypeName): ParameterizedTypeName =
    asClassName().plusParameter(typeName)
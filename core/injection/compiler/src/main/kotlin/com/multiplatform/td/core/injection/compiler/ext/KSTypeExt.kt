package com.multiplatform.td.core.injection.compiler.ext

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName

internal fun KSType.isParameterized(): Boolean = arguments.isNotEmpty()
internal fun KSType.requireClassName(): ClassName = toClassName()
internal fun KSType.requireTypeName(): TypeName = toTypeName()

internal fun KSType.asBinderClassName(klass: KSClassDeclaration? = null): ClassName {
    val className = requireClassName()
    val packageName = klass?.packageName() ?: className.packageName
    return ClassName(packageName, "${className.simpleName}Binder")
}
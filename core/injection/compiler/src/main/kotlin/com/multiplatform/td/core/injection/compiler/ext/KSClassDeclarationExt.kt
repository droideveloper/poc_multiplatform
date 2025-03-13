package com.multiplatform.td.core.injection.compiler.ext

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.multiplatform.td.core.injection.binding.ContributesBinder
import com.multiplatform.td.core.injection.binding.ContributesViewModel
import com.squareup.kotlinpoet.ClassName

internal fun KSClassDeclaration.asBinderClassName(): ClassName =
    ClassName(packageName.asString(), "${simpleName.asString()}Binder")

internal fun KSClassDeclaration.asBinderFactoryClassName(): ClassName =
    ClassName(packageName.asString(), "${asFactoryName()}Binder")

internal fun KSClassDeclaration.asFactoryName(): String =
    "${simpleName.asString()}Factory"

internal fun KSClassDeclaration.singleSuperKSType(): KSType =
    superTypes.single().resolve()

internal fun KSClassDeclaration.singleBinderScopeKSType(): KSType? {
    val annotation = annotations.single { it.asShortName() == ContributesBinder::class.simpleName }
    return annotation.arguments.firstOrNull { it.requireName() == "scope" }?.value as? KSType
}

internal fun KSClassDeclaration.singleBinderBoundKSType(): KSType? {
    val annotation = annotations.single { it.asShortName() == ContributesBinder::class.simpleName }
    return annotation.arguments.firstOrNull { it.requireName() == "boundType" }?.value as? KSType
}

internal fun KSClassDeclaration.singleBinderUseProperty(): Boolean {
    val annotation = annotations.single { it.asShortName() == ContributesBinder::class.simpleName }
    return annotation.arguments.firstOrNull { it.requireName() == "useProperty" }?.value as? Boolean ?: false
}

internal fun KSClassDeclaration.singleViewModelScopeKSType(): KSType? {
    val annotation = annotations.single { it.asShortName() == ContributesViewModel::class.simpleName }
    return annotation.arguments.firstOrNull { it.requireName() == "scope" }?.value as? KSType
}

internal fun KSClassDeclaration.singleViewModelBoundKSType(): KSType? {
    val annotation = annotations.single { it.asShortName() == ContributesViewModel::class.simpleName }
    println("${annotation.shortName} -> args: ${annotation.arguments.joinToString { it.requireName() }}")
    return annotation.arguments.firstOrNull { it.requireName() == "boundType" }?.value as? KSType
}

internal fun KSClassDeclaration.singleViewModelUseProperty(): Boolean {
    val annotation = annotations.single { it.asShortName() == ContributesViewModel::class.simpleName }
    return annotation.arguments.firstOrNull { it.requireName() == "useProperty"}?.value as? Boolean ?: false
}
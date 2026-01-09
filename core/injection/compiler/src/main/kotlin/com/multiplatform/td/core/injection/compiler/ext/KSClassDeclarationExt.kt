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

internal data class BinderArgs(
    val scope: KSType?,
    val boundType: KSType?,
    val parameterizedBoundType: KSType?,
    val useProperty: Boolean,
) {

    val isBoundType: Boolean = boundType != null
    val isParameterizedType: Boolean = isBoundType && parameterizedBoundType != null
}

internal fun KSClassDeclaration.sequenceBinderArgs(): Sequence<BinderArgs> {
    val items = annotations.filter { it.asShortName() == ContributesBinder::class.simpleName }
    return items.map { annotation ->
        BinderArgs(
            scope = annotation.arguments.firstOrNull {
                it.requireName() == ArgumentName.Scope.name
            }?.value as? KSType,
            boundType = annotation.arguments.firstOrNull {
                it.requireName() == ArgumentName.BoundType.name
            }?.requireValue(),
            parameterizedBoundType = annotation.arguments.firstOrNull {
                it.requireName() == ArgumentName.ParameterizedBoundType.name
            }?.requireValue(),
            useProperty = annotation.arguments.firstOrNull {
                it.requireName() == ArgumentName.UseProperty.name
            }?.value as? Boolean ?: false,
        )
    }
}

internal fun KSClassDeclaration.sequenceViewModelBinderArgs(): Sequence<BinderArgs> {
    val items = annotations.filter { it.asShortName() == ContributesViewModel::class.simpleName }
    return items.map { annotation ->
        BinderArgs(
            scope = annotation.arguments.firstOrNull {
                it.requireName() == ArgumentName.Scope.name
            }?.value as? KSType,
            boundType = annotation.arguments.firstOrNull {
                it.requireName() == ArgumentName.BoundType.name
            }?.requireValue(),
            parameterizedBoundType = null,
            useProperty = annotation.arguments.firstOrNull {
                it.requireName() == ArgumentName.UseProperty.name
            }?.value as? Boolean ?: false,
        )
    }
}

sealed interface ArgumentName {
    val name: String

    data object Scope : ArgumentName {
        override val name: String = "scope"
    }

    data object BoundType : ArgumentName {
        override val name: String = "boundType"
    }

    data object ParameterizedBoundType : ArgumentName {
        override val name: String = "parameterizedBoundType"
    }

    data object UseProperty : ArgumentName {
        override val name: String = "useProperty"
    }
}

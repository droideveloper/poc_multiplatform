package com.multiplatform.td.core.injection.compiler.spec

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.symbol.KSValueParameter
import com.multiplatform.td.core.injection.Binder
import com.multiplatform.td.core.injection.compiler.ext.asBinderClassName
import com.multiplatform.td.core.injection.compiler.ext.asBinderFactoryClassName
import com.multiplatform.td.core.injection.compiler.ext.asFactoryName
import com.multiplatform.td.core.injection.compiler.ext.isAssisted
import com.multiplatform.td.core.injection.compiler.ext.isParameterized
import com.multiplatform.td.core.injection.compiler.ext.requireClassName
import com.multiplatform.td.core.injection.compiler.ext.requireContainingFile
import com.multiplatform.td.core.injection.compiler.ext.requireName
import com.multiplatform.td.core.injection.compiler.ext.requireParameterizedType
import com.multiplatform.td.core.injection.compiler.ext.requireTypeName
import com.multiplatform.td.core.injection.compiler.ext.singleBinderBoundKSType
import com.multiplatform.td.core.injection.compiler.ext.singleSuperKSType
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeAliasSpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.addOriginatingKSFile
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toKModifier
import com.squareup.kotlinpoet.ksp.writeTo
import me.tatarka.inject.annotations.Inject

internal sealed class Binding(
    open val klass: KSClassDeclaration,
) {

    companion object {
        fun resolve(klass: KSClassDeclaration): Binding {
            val superType = klass.singleBinderBoundKSType() ?: klass.singleSuperKSType()
            return when {
                superType.isParameterized() -> ParameterizedSpec(klass)
                else -> InterfaceSpec(klass)
            }
        }
    }

    data class InterfaceSpec(
        override val klass: KSClassDeclaration,
    ) : Binding(klass) {

        override val superType: KSType
            get() = klass.singleBinderBoundKSType() ?: klass.singleSuperKSType()

        override val typeSpecBuilder: TypeSpec.Builder
            get() = TypeSpec.classBuilder(superType.asBinderClassName(klass))

        override val fileSpecBuilder: FileSpec.Builder
            get() = FileSpec.builder(superType.asBinderClassName(klass))
    }

    data class ParameterizedSpec(
        override val klass: KSClassDeclaration,
    ) : Binding(klass) {

        override val superType: KSType
            get() = klass.asType(emptyList())

        override val typeSpecBuilder: TypeSpec.Builder
            get() = TypeSpec.classBuilder(klass.asBinderClassName())
                .addModifiers(requireNotNull(klass.modifiers.single().toKModifier()))

        override val fileSpecBuilder: FileSpec.Builder
            get() = FileSpec.builder(klass.asBinderClassName())
    }

    data class ViewModelSpec(
        override val klass: KSClassDeclaration,
    ) : Binding(klass) {

        private val typeAliasSpec: TypeAliasSpec
            get() = TypeAliasSpec.builder(klass.asFactoryName(), lambdaType)
                .addModifiers(KModifier.INTERNAL)
                .build()

        private val lambdaType: LambdaTypeName
            get() = LambdaTypeName.get(
                parameters = assistedParameters,
                returnType = superTypeOrClassName(),
            )

        override val superType: KSType
            get() = klass.asType(emptyList())

        override val typeSpecBuilder: TypeSpec.Builder
            get() = TypeSpec.classBuilder(klass.asBinderFactoryClassName())
                .addModifiers(requireNotNull(klass.modifiers.single().toKModifier()))

        override val fileSpecBuilder: FileSpec.Builder
            get() = FileSpec.builder(klass.asBinderFactoryClassName())

        override val parameterizedSuperType: ParameterizedTypeName
            get() = Binder::class.requireParameterizedType(asClassName())

        override val invokeSpec: FunSpec
            get() {
                var statement = constructorParameters
                    .filterNot { it.isAssisted() }
                    .joinToString(separator = "\n") { parameter ->
                        val name = parameter.requireName()
                        "$name = $name,"
                    }

                val assisted = constructorParameters
                    .filter { it.isAssisted() }

                val hasAnyAssisted = assistedParameters.isNotEmpty()
                if (statement.isNotEmpty() && hasAnyAssisted) statement += "\n"

                statement += assistedParameters
                    .joinToString(separator = "\n") { parameter ->
                        val name = parameter.name
                        "$name = $name,"
                    }

                if (statement.isNotEmpty()) statement = "\n$statement\n"

                val lambdaArgs = when {
                    hasAnyAssisted -> assisted.joinToString(separator = ",") { parameter ->
                        "${parameter.requireName()}: ${parameter.type.resolve().toClassName().simpleName}"
                    } + " ->\n"
                    else -> "\n"
                }

                return FunSpec.builder("invoke")
                    .addModifiers(KModifier.OVERRIDE, KModifier.OPERATOR)
                    .addStatement("return { $lambdaArgs ⇥%T(⇥$statement⇤)⇤ \n}", klass.toClassName())
                    .returns(asClassName())
                    .build()
            }

        override fun codeGenerate(codeGenerator: CodeGenerator) {
            val newFileSpec = fileSpec.toBuilder()
                .addTypeAlias(typeAliasSpec)
                .build()

            newFileSpec.writeTo(codeGenerator, aggregating = false)
        }

        internal fun asClassName(): ClassName =
            ClassName(klass.packageName.asString(), typeAliasSpec.name)
    }

    internal abstract val superType: KSType
    protected abstract val typeSpecBuilder: TypeSpec.Builder
    protected abstract val fileSpecBuilder: FileSpec.Builder

    internal open fun codeGenerate(codeGenerator: CodeGenerator) {
        fileSpec.writeTo(
            codeGenerator = codeGenerator,
            aggregating = true,
            originatingKSFiles = listOf(klass.requireContainingFile()).asIterable(),
        )
    }

    protected open val parameterizedSuperType: ParameterizedTypeName
        get() = Binder::class.requireParameterizedType(superTypeOrClassName())

    protected val constructorParameters: List<KSValueParameter>
        get() = klass.primaryConstructor?.parameters ?: emptyList()

    private val parameterSpecs: List<ParameterSpec>
        get() = constructorParameters
            .filterNot { it.isAssisted() }
            .map { parameter ->
                val name = parameter.requireName()
                val type = parameter.type.resolve().requireClassName()
                ParameterSpec.builder(name, type).build()
            }

    protected val assistedParameters: List<ParameterSpec>
        get() = constructorParameters.filter { it.isAssisted() }
            .map { parameter ->
                val name = parameter.requireName()
                val type = parameter.type.resolve().requireClassName()
                ParameterSpec.builder(name, type).build()
            }

    private val propertySpecs: List<PropertySpec>
        get() = constructorParameters
            .filterNot { it.isAssisted() }
            .map { parameter ->
                val name = parameter.requireName()
                val type = parameter.type.resolve().requireClassName()
                PropertySpec.builder(name, type)
                    .initializer(name)
                    .addModifiers(KModifier.PRIVATE)
                    .build()
            }

    private val constructorSpec: FunSpec
        get() = FunSpec.constructorBuilder()
            .addParameters(parameterSpecs)
            .build()

    protected open val invokeSpec: FunSpec
        get() {
            var statement = constructorParameters.joinToString(separator = "\n") { parameter ->
                val name = parameter.requireName()
                "$name = $name,"
            }
            if (statement.isNotEmpty()) statement = "\n$statement\n"

            return FunSpec.builder("invoke")
                .addModifiers(KModifier.OVERRIDE, KModifier.OPERATOR)
                .addStatement("return \n⇥%T(⇥$statement⇤)⇤", klass.toClassName())
                .returns(superTypeOrClassName())
                .build()
        }

    protected fun superTypeOrClassName() = when {
        superType.isParameterized() -> superType.requireTypeName()
        else -> superType.requireClassName()
    }

    private val typeSpec: TypeSpec
        get() = typeSpecBuilder
            .addOriginatingKSFile(klass.requireContainingFile())
            .addAnnotation(Inject::class)
            .primaryConstructor(constructorSpec)
            .addProperties(propertySpecs)
            .addSuperinterface(parameterizedSuperType)
            .addFunction(invokeSpec)
            .build()

    protected val fileSpec: FileSpec
        get() = fileSpecBuilder
            .addType(typeSpec)
            .build()
}

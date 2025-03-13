package com.multiplatform.td.core.injection.compiler.spec

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.multiplatform.td.core.injection.compiler.ext.asBinderClassName
import com.multiplatform.td.core.injection.compiler.ext.asBinderFactoryClassName
import com.multiplatform.td.core.injection.compiler.ext.isParameterized
import com.multiplatform.td.core.injection.compiler.ext.requireClassName
import com.multiplatform.td.core.injection.compiler.ext.requireContainingFile
import com.multiplatform.td.core.injection.compiler.ext.singleBinderScopeKSType
import com.multiplatform.td.core.injection.compiler.ext.singleBinderUseProperty
import com.multiplatform.td.core.injection.compiler.ext.singleViewModelScopeKSType
import com.multiplatform.td.core.injection.compiler.ext.singleViewModelUseProperty
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.addOriginatingKSFile
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toKModifier
import com.squareup.kotlinpoet.ksp.writeTo
import me.tatarka.inject.annotations.Provides

internal sealed class Injection(
    open val bindings: List<Binding>,
    open val logger: KSPLogger,
) {
    data class Default(
        override val bindings: List<Binding>,
        override val logger: KSPLogger,
    ) : Injection(bindings, logger) {

        override fun toModuleClassName(bindings: List<Binding>): ClassName {
            val packageNames = bindings.map { it.klass.packageName.asString() }
            val packageName = packageNames(packageNames)
            return ClassName(packageName,"GeneratedBinderModule")
        }
    }

    data class ViewModel(
        override val bindings: List<Binding>,
        override val logger: KSPLogger,
    ) : Injection(bindings, logger) {

        override fun toModuleClassName(bindings: List<Binding>): ClassName {
            val packageNames = bindings.map { it.klass.packageName.asString() }
            val packageName = packageNames(packageNames)
            return ClassName(packageName, "GeneratedViewModelModule")
        }

        override fun scopedFunSpecOrDefault(binding: Binding): FunSpec {
            val scope = binding.klass.singleViewModelScopeKSType()
            return when {
                scope == null -> funSpecBuilder.build()
                else -> funSpecBuilder.addAnnotation(scope.toClassName()).build()
            }
        }

        override fun scopeClassNameOrNull(binding: Binding): ClassName? {
            val scope = binding.klass.singleViewModelScopeKSType()
            return when {
                scope == null -> null
                else -> scope.toClassName()
            }
        }

        override fun usePropertyOrFun(binding: Binding): Boolean {
            return binding.klass.singleViewModelUseProperty()
        }

        override fun binderSuperTypeOrClassName(binding: Binding): ClassName {
            return binding.klass.asBinderFactoryClassName()
        }

        override fun superTypeOrClassName(binding: Binding): ClassName {
            require(binding is Binding.ViewModelSpec)
            return binding.asClassName()
        }

        override val typeSpecBuilder: TypeSpec.Builder
            get() {
                val builder = super.typeSpecBuilder
                val factories = bindings.map { binding ->
                    PropertySpec.builder(
                        name = superTypeOrClassName(binding).simpleName.replaceFirstChar { it.lowercaseChar() },
                        type = superTypeOrClassName(binding)
                    )
                        .addModifiers(superTypeOrClassNameModifier(binding))
                        .addModifiers(KModifier.ABSTRACT)
                        .mutable(false)
                        .build()
                }
                return builder.addProperties(factories)
            }
    }

    private val module: ClassName
        get() = toModuleClassName(bindings)

    private val hasAnyInternalBinding: Boolean
        get() = bindings.any { it !is Binding.InterfaceSpec }

    protected open val typeSpecBuilder: TypeSpec.Builder
        get() = when {
            hasAnyInternalBinding -> TypeSpec.classBuilder(module)
                .addModifiers(KModifier.ABSTRACT)
            else -> TypeSpec.interfaceBuilder(module)
        }

    protected val funSpecBuilder: FunSpec.Builder
        get() = FunSpec.getterBuilder()
            .addAnnotation(Provides::class)
            .addStatement("return invoke()")

    private val propertySpecs: List<PropertySpec>
        get() = bindings.map { binding ->
            PropertySpec.builder("bind", superTypeOrClassName(binding))
                .addOriginatingKSFile(binding.klass.requireContainingFile())
                .receiver(binderSuperTypeOrClassName(binding))
                .addModifiers(superTypeOrClassNameModifier(binding))
                .mutable(false)
                .getter(scopedFunSpecOrDefault(binding))
                .build()
        }

    private val funSpecs: List<FunSpec>
        get() = bindings.map { binding ->
            val builder = FunSpec.builder("bind${superTypeOrClassName(binding).simpleName}")
                .addOriginatingKSFile(binding.klass.requireContainingFile())
                .addModifiers(superTypeOrClassNameModifier(binding))
                .returns(superTypeOrClassName(binding))
                .addAnnotation(Provides::class)
                .addParameter(
                    ParameterSpec.builder("binder", binderSuperTypeOrClassName(binding))
                        .build()
                )
                .addStatement("return binder()")

            val scopeClassName = scopeClassNameOrNull(binding)
            when {
                scopeClassName == null -> builder.build()
                else -> builder.addAnnotation(scopeClassName).build()
            }
        }

    protected open fun scopedFunSpecOrDefault(binding: Binding): FunSpec {
        val scope = binding.klass.singleBinderScopeKSType()
        return when {
            scope == null -> funSpecBuilder.build()
            else -> funSpecBuilder.addAnnotation(scope.toClassName()).build()
        }
    }

    protected open fun scopeClassNameOrNull(binding: Binding): ClassName? {
        val scope = binding.klass.singleBinderScopeKSType()
        return when {
            scope == null -> null
            else -> scope.toClassName()
        }
    }

    protected open fun usePropertyOrFun(binding: Binding): Boolean {
        return binding.klass.singleBinderUseProperty()
    }

    protected fun superTypeOrClassNameModifier(binding: Binding): KModifier {
        return when {
            binding is Binding.InterfaceSpec -> KModifier.PUBLIC
            else -> requireNotNull(binding.klass.modifiers.single().toKModifier())
        }
    }

    protected open fun superTypeOrClassName(binding: Binding): ClassName {
        val superType = binding.superType
        return when {
            superType.isParameterized() -> binding.klass.toClassName()
            else -> superType.requireClassName()
        }
    }

    protected open fun binderSuperTypeOrClassName(binding: Binding): ClassName {
        val superType =  binding.superType
        return when {
            binding is Binding.ParameterizedSpec -> binding.klass.asBinderClassName()
            else -> superType.asBinderClassName(binding.klass)
        }
    }

    internal fun codeGenerate(codeGenerator: CodeGenerator) {
        val properties = propertySpecs.filterIndexed { index, _ -> usePropertyOrFun(bindings[index]) }
        val functions = funSpecs.filterIndexed { index, _ -> usePropertyOrFun(bindings[index]).not() }
        val typeSpec = typeSpecBuilder.addProperties(properties)
            .addFunctions(functions)
            .build()

        val fileSpec = FileSpec.builder(module)
            .addType(typeSpec)
            .build()

        fileSpec.writeTo(
            codeGenerator = codeGenerator,
            aggregating = true,
            originatingKSFiles = bindings.map { binding -> binding.klass.requireContainingFile() }.asIterable(),
        )
    }

    internal fun packageNames(packageNames: List<String>): String {
        var packages = packageNames.distinct()
        return if (packages.count() == 1) {
            packages.single()
        } else {
            packages = removeIfPossiblePackageName(packages)
            packages = packages.map { pack ->
                val dirs = pack.split(".")
                dirs.take(dirs.size - 1).joinToString(separator = ".")
            }
            packageNames(packages)
        }
    }

    private fun removeIfPossiblePackageName(packages: List<String>): List<String> {
        val packs = packages.sortedBy { it.count() }
        return packages.filter { it != packs.firstOrNull() }
    }

    protected abstract fun toModuleClassName(bindings: List<Binding>): ClassName
}

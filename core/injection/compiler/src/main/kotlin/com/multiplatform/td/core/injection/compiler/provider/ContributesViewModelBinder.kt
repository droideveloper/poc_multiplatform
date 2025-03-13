package com.multiplatform.td.core.injection.compiler.provider

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.multiplatform.td.core.injection.binding.ContributesViewModel
import com.multiplatform.td.core.injection.compiler.ext.getSymbolsWithAnnotation
import com.multiplatform.td.core.injection.compiler.spec.Binding
import com.multiplatform.td.core.injection.compiler.spec.Injection

internal class ContributesViewModelBinder(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val bindings = resolver
            .getSymbolsWithAnnotation(ContributesViewModel::class)
            .filterIsInstance<KSClassDeclaration>()
            .map { Binding.ViewModelSpec(it) }
            .toList()

        bindings.forEach { spec ->
            spec.codeGenerate(codeGenerator)
        }

        if (bindings.isNotEmpty()) {
            val injection = Injection.ViewModel(bindings, logger)
            injection.codeGenerate(codeGenerator)
        }
        return emptyList()
    }
}

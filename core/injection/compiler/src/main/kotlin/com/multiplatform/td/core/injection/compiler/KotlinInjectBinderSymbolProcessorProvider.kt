package com.multiplatform.td.core.injection.compiler

import com.google.auto.service.AutoService
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.multiplatform.td.core.injection.compiler.provider.ContributesBinderProcessor
import com.multiplatform.td.core.injection.compiler.provider.ContributesViewModelBinder
import com.multiplatform.td.core.injection.compiler.provider.MultipleSymbolProcessor

@AutoService(SymbolProcessorProvider::class)
class KotlinInjectBinderSymbolProcessorProvider : SymbolProcessorProvider {

    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return MultipleSymbolProcessor(
            setOf(
                ContributesBinderProcessor(
                    environment.codeGenerator,
                    environment.logger,
                ),
                ContributesViewModelBinder(
                    environment.codeGenerator,
                    environment.logger,
                )
            )
        )
    }
}
package com.multiplatform.td.core.injection.compiler.provider

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated

internal class MultipleSymbolProcessor(
    private val processors: Collection<SymbolProcessor>,
) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        return processors.flatMap { it.process(resolver) }
    }

    override fun finish() {
        processors.forEach { it.finish() }
    }

    override fun onError() {
        processors.forEach { it.onError() }
    }
}
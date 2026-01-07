@file:OptIn(ExperimentalCompilerApi::class)

package com.multiplatform.td.core.injection.compiler.framework

import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.multiplatform.td.core.injection.compiler.KotlinInjectBinderSymbolProcessorProvider
import com.tschuchort.compiletesting.CompilationResult
import com.tschuchort.compiletesting.DiagnosticSeverity
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.KspTool
import com.tschuchort.compiletesting.SourceFile
import com.tschuchort.compiletesting.configureKsp
import com.tschuchort.compiletesting.kspWithCompilation
import org.intellij.lang.annotations.Language
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import java.io.File

class ProjectCompiler(
    private val workingDir: File,
) {

    private val sourceFiles = mutableListOf<SourceFile>()
    private val symbolProcessors = mutableListOf<SymbolProcessorProvider>()

    fun source(fileName: String, @Language("kotlin") source: String): ProjectCompiler {
        sourceFiles.add(SourceFile.kotlin(fileName, source))
        return this
    }

    fun symbolProcessor(processor: SymbolProcessorProvider): ProjectCompiler {
        symbolProcessors.add(processor)
        return this
    }

    fun compile(): TestCompilationResult {
        val result = TestCompilationResult(
            KotlinCompilation().apply {
                workingDir = this@ProjectCompiler.workingDir
                sources = sourceFiles

                val ksp: KspTool.() -> Unit = {
                    symbolProcessorProviders.add(KotlinInjectBinderSymbolProcessorProvider())
                    symbolProcessorProviders.addAll(symbolProcessors)
                }

                configureKsp(ksp)

                inheritClassPath = true
                // work-around for https://github.com/ZacSweers/kotlin-compile-testing/issues/197
                kspWithCompilation = true
                messageOutputStream = System.out
            }.compile()
        )

        if (result.success.not()) {
            throw IllegalStateException(result.output(DiagnosticSeverity.ERROR))
        }
        return result
    }
}

private fun String.filterByKind(vararg kind: DiagnosticSeverity): String = buildString {
    var currentKind: DiagnosticSeverity? = null
    for (line in this@filterByKind.lineSequence()) {
        val lineKind = line.matchLine()
        if (lineKind != null) {
            currentKind = lineKind
        }
        if (currentKind in kind) {
            append(line)
            append('\n')
        }
    }
}

private fun String.matchLine(): DiagnosticSeverity? {
    if (length < 2) return null
    val matchedKind = when (get(0)) {
        'e' -> DiagnosticSeverity.ERROR
        'w' -> DiagnosticSeverity.WARNING
        'v' -> DiagnosticSeverity.LOGGING
        else -> null
    } ?: return null

    return if (get(1) == ':') {
        matchedKind
    } else {
        null
    }
}

class TestCompilationResult(val compilation: CompilationResult) {
    val success: Boolean
        get() = compilation.exitCode == KotlinCompilation.ExitCode.OK

    fun output(vararg severities: DiagnosticSeverity): String =
        compilation.messages.filterByKind(*severities)
}

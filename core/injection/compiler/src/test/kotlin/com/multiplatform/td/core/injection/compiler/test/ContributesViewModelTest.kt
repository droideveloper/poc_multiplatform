@file:OptIn(ExperimentalCompilerApi::class)

package com.multiplatform.td.core.injection.compiler.test

import com.multiplatform.td.core.injection.compiler.framework.ProjectCompiler
import com.tschuchort.compiletesting.JvmCompilationResult
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.fail
import org.junit.jupiter.api.io.TempDir
import java.io.File

class ContributesViewModelTest {

    @TempDir
    lateinit var workingDir: File

    @Test
    fun `given ContributesViewModel will provide view model modules`() {
        val projectCompiler = ProjectCompiler(workingDir)
            .source("ViewModel.kt",
                """
                   package com.multiplatform.td.core.test

                   open class ViewModel {
                   }
                """.trimIndent()
            )
            .source("CityViewModel.kt",
                """
                    package com.multiplatform.td.core.test

                    import com.multiplatform.td.core.injection.binding.ContributesViewModel
                    import com.multiplatform.td.core.injection.scopes.FeatureScope

                    @ContributesViewModel(scope = FeatureScope::class)
                    internal class CityViewModel : ViewModel() {
                    }
                """.trimIndent())

        assertDoesNotThrow {
            val result = projectCompiler.compile()
            val compilation = result.compilation as JvmCompilationResult

            val module = Class.forName(
                "com.multiplatform.td.core.test.GeneratedViewModelModule",
                true,
                compilation.classLoader,
            )
            assert(module.methods.any { method -> method.name.contains("bindCityViewModelFactoryBinder") }) {
                "bindCityViewModelFactoryBinder not found on GeneratedViewModelModule"
            }

            val binder = Class.forName(
                "com.multiplatform.td.core.test.CityViewModelFactoryBinder",
                true,
                compilation.classLoader,
            )
            assert(binder.methods.any { method -> method.name == "invoke" }) {
                "invoke not found on CityViewModelFactoryBinder"
            }
        }
    }
}

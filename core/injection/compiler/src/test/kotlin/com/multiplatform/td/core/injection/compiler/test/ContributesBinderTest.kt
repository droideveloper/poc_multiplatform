@file:OptIn(ExperimentalCompilerApi::class)

package com.multiplatform.td.core.injection.compiler.test

import com.multiplatform.td.core.injection.compiler.framework.ProjectCompiler
import com.tschuchort.compiletesting.JvmCompilationResult
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.io.TempDir
import java.io.File

class ContributesBinderTest {

    @TempDir
    lateinit var workingDir: File

    @Test
    fun `given ContributeBinder multiple will generate proper binders`() {
        val projectCompiler = ProjectCompiler(workingDir)
            .source("Environment.kt",
                """
                    package com.multiplatform.td.core.test

                   interface Environment {
                   }
                """.trimIndent()
                )
            .source("Initializer.kt",
                """
                    package com.multiplatform.td.core.test

                    interface Initializer<T> {
                    }
            """.trimIndent()
            )
            .source(
                "EnvironmentImpl.kt",
                """
                    package com.multiplatform.td.core.test

                    import com.multiplatform.td.core.injection.binding.ContributesBinder
                    import com.multiplatform.td.core.injection.scopes.AppScope

                    @ContributesBinder(
                        scope = AppScope::class,
                        boundType = Environment::class,
                    )
                    @ContributesBinder(
                        scope = AppScope::class,
                        boundType = Initializer::class,
                        parameterizedBoundType = Environment::class,
                    )
                    internal class EnvironmentImpl : Environment, Initializer<Environment> {
                    }
                """.trimIndent(),
            )


        assertDoesNotThrow {
            val result = projectCompiler.compile()
            val compilation = result.compilation as JvmCompilationResult

            val module = Class.forName(
                "com.multiplatform.td.core.test.GeneratedBinderModule",
                true,
                compilation.classLoader,
            )
            assert(module.methods.any { method -> method.name == "bindEnvironmentBinder" }) {
                "bindEnvironmentBinder not found on GeneratedBinderModule"
            }
            assert(module.methods.any { method -> method.name == "bindEnvironmentInitializerBinder" }) {
                "bindEnvironmentInitializerBinder not found on GeneratedBinderModule"
            }

            val envBinder = Class.forName(
                "com.multiplatform.td.core.test.EnvironmentBinder",
                true,
                compilation.classLoader,
            )
            assert(envBinder.methods.any { method -> method.name == "invoke" }) {
                "invoke not found on EnvironmentBinder"
            }

            val envInitializerBinder = Class.forName(
                "com.multiplatform.td.core.test.EnvironmentInitializerBinder",
                true,
                compilation.classLoader,
            )
            assert(envInitializerBinder.methods.any { method -> method.name == "invoke" }) {
                "invoke not found on EnvironmentInitializerBinder"
            }
        }
    }

    @Test
    fun `given ContributeBinder single interface will generate proper binders`() {
        val projectCompiler = ProjectCompiler(workingDir)
            .source("Environment.kt",
                """
                    package com.multiplatform.td.core.test

                   interface Environment {
                   }
                """.trimIndent()
            )
            .source(
                "EnvironmentImpl.kt",
                """
                    package com.multiplatform.td.core.test

                    import com.multiplatform.td.core.injection.binding.ContributesBinder
                    import com.multiplatform.td.core.injection.scopes.AppScope

                    @ContributesBinder(
                        scope = AppScope::class,
                        boundType = Environment::class,
                    )
                    internal class EnvironmentImpl : Environment {
                    }
                """.trimIndent(),
            )


        assertDoesNotThrow {
            val result = projectCompiler.compile()
            val compilation = result.compilation as JvmCompilationResult

            val module = Class.forName(
                "com.multiplatform.td.core.test.GeneratedBinderModule",
                true,
                compilation.classLoader,
            )
            assert(module.methods.any { method -> method.name == "bindEnvironmentBinder" }) {
                "bindEnvironmentBinder not found on GeneratedBinderModule"
            }

            val envBinder = Class.forName(
                "com.multiplatform.td.core.test.EnvironmentBinder",
                true,
                compilation.classLoader,
            )
            assert(envBinder.methods.any { method -> method.name == "invoke" }) {
                "invoke not found on EnvironmentBinder"
            }
        }
    }

    @Test
    fun `given ContributeBinder single parameterized will generate proper binders`() {
        val projectCompiler = ProjectCompiler(workingDir)
            .source("Environment.kt",
                """
                    package com.multiplatform.td.core.test

                   interface Environment {
                   }
                """.trimIndent()
            )
            .source("Initializer.kt",
                """
                    package com.multiplatform.td.core.test

                    interface Initializer<T> {
                    }
            """.trimIndent()
            )
            .source(
                "EnvironmentImpl.kt",
                """
                    package com.multiplatform.td.core.test

                    import com.multiplatform.td.core.injection.binding.ContributesBinder
                    import com.multiplatform.td.core.injection.scopes.AppScope

                    @ContributesBinder(
                        scope = AppScope::class,
                        boundType = Initializer::class,
                        parameterizedBoundType = Environment::class,
                    )
                    internal class EnvironmentImpl : Initializer<Environment> {
                    }
                """.trimIndent(),
            )


        assertDoesNotThrow {
            val result = projectCompiler.compile()
            val compilation = result.compilation as JvmCompilationResult

            val module = Class.forName(
                "com.multiplatform.td.core.test.GeneratedBinderModule",
                true,
                compilation.classLoader,
            )
            assert(module.methods.any { method -> method.name == "bindEnvironmentInitializerBinder" }) {
                "bindEnvironmentInitializerBinder not found on GeneratedBinderModule"
            }

            val envInitializerBinder = Class.forName(
                "com.multiplatform.td.core.test.EnvironmentInitializerBinder",
                true,
                compilation.classLoader,
            )
            assert(envInitializerBinder.methods.any { method -> method.name == "invoke" }) {
                "invoke not found on EnvironmentInitializerBinder"
            }
        }
    }
}



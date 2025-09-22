package com.multiplatform.td.conventions

import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.LibraryPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.compose.ComposePlugin
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradleSubplugin
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginWrapper

class UiMultiplatformPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(KotlinMultiplatformPluginWrapper::class)
                apply(LibraryPlugin::class)
                apply(LanguageLintPlugin::class)
                apply(ComposePlugin::class)
                apply(ComposeCompilerGradleSubplugin::class)
                apply(MetadataPlugin::class)
            }
            val compose = project.dependencies.extensions.getByType<ComposePlugin.Dependencies>()

            extensions.getByType<LibraryExtension>().apply {
                configureAndroidLibrary(target)
            }

            extensions.getByType<KotlinMultiplatformExtension>().apply {
                configureMultiplatformLibrary()
                applyCommonCompose(compose, project)
                applyAndroidCompose(compose, project)

                val disabledTargets = targets.filter { it.publishable.not() }.toList()
                println("${target.name}/disabled -> ${disabledTargets.joinToString { it.name }}")
            }

            dependencies {
                add("debugImplementation", compose.uiTooling)
            }
        }
    }
}

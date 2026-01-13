package com.multiplatform.td.conventions

import com.android.build.api.variant.KotlinMultiplatformAndroidComponentsExtension
import com.android.build.gradle.api.KotlinMultiplatformAndroidPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.compose.ComposePlugin
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradleSubplugin
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginWrapper
import kotlin.apply

class UiMultiplatformPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(KotlinMultiplatformPluginWrapper::class)
                apply(KotlinMultiplatformAndroidPlugin::class)
                apply(LanguageLintPlugin::class)
                apply(ComposePlugin::class)
                apply(ComposeCompilerGradleSubplugin::class)
                apply(MetadataPlugin::class)
            }
            val compose = project.dependencies.extensions.getByType<ComposePlugin.Dependencies>()

            extensions.getByType<KotlinMultiplatformAndroidComponentsExtension>().apply {
                configureAndroidLibrary(target, true)
            }

            extensions.getByType<KotlinMultiplatformExtension>().apply {
                configureMultiplatformDefaults {}
                applyCommonCompose(compose, project)
                applyAndroidCompose(compose, project)
            }

            dependencies {
                "androidRuntimeClasspath"(compose.uiTooling)
            }
        }
    }
}

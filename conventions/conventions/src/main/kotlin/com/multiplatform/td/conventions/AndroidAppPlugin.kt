package com.multiplatform.td.conventions

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.AppPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.compose.ComposePlugin
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginWrapper
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradleSubplugin

class AndroidAppPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(AppPlugin::class)
                apply(KotlinMultiplatformPluginWrapper::class)
                apply(ComposePlugin::class)
                apply(ComposeCompilerGradleSubplugin::class)
                apply(MetadataPlugin::class)
            }
            val compose = project.dependencies.extensions.getByType<ComposePlugin.Dependencies>()

            extensions.getByType<ApplicationExtension>().apply {
                configureAndroidApplication(target)
            }

            extensions.getByType<KotlinMultiplatformExtension>().apply {
                configureMultiplatformLibrary()
                applyCommonCompose(compose, project)
                applyAndroidCompose(compose, project)
            }

            dependencies {
                add("debugImplementation", compose.uiTooling)
            }
        }
    }
}



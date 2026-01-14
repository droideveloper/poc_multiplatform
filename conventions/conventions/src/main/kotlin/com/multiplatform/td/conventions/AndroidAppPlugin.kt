package com.multiplatform.td.conventions

import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.android.build.gradle.AppPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.compose.ComposePlugin
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradleSubplugin
import org.jetbrains.kotlin.gradle.plugin.KotlinAndroidPluginWrapper
import kotlin.apply

class AndroidAppPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(AppPlugin::class)
                apply(KotlinAndroidPluginWrapper::class)
                apply(LanguageLintPlugin::class)
                apply(ComposePlugin::class)
                apply(ComposeCompilerGradleSubplugin::class)
            }

            extensions.getByType<ApplicationAndroidComponentsExtension>().apply {
                configureAndroidApplication(target)
            }

            dependencies {
                add("implementation", composeRuntime.asDependency())
                add("implementation", composeFoundation.asDependency())
                add("implementation", composeMaterial3.asDependency())
                add("implementation", composeUi.asDependency())
                add("implementation", composeComponentsResources.asDependency())
                add("implementation", composePreview.asDependency())
                add("implementation", androidxLifecycleViewModel.asDependency())
                add("implementation", androidxLifecycleRuntimeCompose.asDependency())

                add("implementation", androidxActivityCompose.asDependency())

                add("debugImplementation", composeUiTooling.asDependency())

                add("testImplementation", jUnit.asDependency())
                add("testImplementation", espressoCore.asDependency())
                add("testImplementation", androidxTestJunit.asDependency())
                add("testImplementation", androidxTestJunit4.asDependency())
                add("testImplementation", androidxTestManifest.asDependency())
                add("testImplementation", robolectric.asDependency())
            }
        }
    }
}



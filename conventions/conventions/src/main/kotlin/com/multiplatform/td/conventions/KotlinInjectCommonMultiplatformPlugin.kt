package com.multiplatform.td.conventions

import com.google.devtools.ksp.gradle.KspExtension
import com.google.devtools.ksp.gradle.KspGradleSubplugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginWrapper

class KotlinInjectCommonMultiplatformPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(KotlinMultiplatformPluginWrapper::class)
            apply(KspGradleSubplugin::class)
            apply(ContributeKspPlugin::class)
        }

        extensions.getByType<KotlinMultiplatformExtension>().apply {
            sourceSets.commonMain.configure {
                dependencies {
                    api(kotlinInjectRuntime.asDependency())
                }
            }
        }

        extensions.getByType<KspExtension>().apply {
            arg("me.tatarka.inject.generateCompanionExtensions", "true")
            // arg("me.tatarka.inject.dumpGraph", "true")
        }

        configureKotlinInjectCommonDependencies()
    }
}
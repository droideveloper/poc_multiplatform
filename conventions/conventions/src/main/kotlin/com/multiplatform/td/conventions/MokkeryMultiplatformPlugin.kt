package com.multiplatform.td.conventions

import dev.mokkery.gradle.MokkeryGradlePlugin
import dev.mokkery.gradle.mokkery
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginWrapper

class MokkeryMultiplatformPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(KotlinMultiplatformPluginWrapper::class)
            apply(MokkeryGradlePlugin::class)
        }

        extensions.getByType<KotlinMultiplatformExtension>().apply {
            sourceSets.commonTest.configure {
                dependencies {
                    implementation(kotlinTest.asDependency())
                    implementation(kotlinCoroutinesTest.asDependency())
                    implementation(mokkery("runtime"))
                    implementation(mokkery("core"))
                    implementation(mokkery("coroutines"))
                }
            }
        }

        dependencies {}
    }
}

package com.multiplatform.td.conventions

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class CommonMultiplatformPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(MultiplatformPlugin::class)
            apply(MetadataPlugin::class)
        }
        extensions.getByType<KotlinMultiplatformExtension>().apply {
            sourceSets.commonMain.configure {
                dependencies {
                    implementation(kotlinCoroutinesCore.asDependency())
                    implementation(kotlinDatetime.asDependency())
                }
            }
        }

        dependencies {}
    }
}

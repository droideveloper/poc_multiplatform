package com.multiplatform.td.conventions

import androidx.room.gradle.RoomExtension
import androidx.room.gradle.RoomGradlePlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginWrapper

class RoomMultiplatformPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(KotlinMultiplatformPluginWrapper::class)
            apply(KspMultiplatformPlugin::class)
            apply(RoomGradlePlugin::class)
        }

        extensions.getByType<RoomExtension>().apply {
            schemaDirectory(
                "${project.projectDir.path}/schema"
            )
        }

        extensions.getByType<KotlinMultiplatformExtension>().apply {
            sourceSets.commonMain.configure {
                dependencies {
                    implementation(roomRuntime.asDependency())
                }
            }
        }

        dependencies {
            configureKspDependencies()
        }
    }
}

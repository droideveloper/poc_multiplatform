package com.multiplatform.td.conventions

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginWrapper
import org.jetbrains.kotlin.gradle.tasks.KotlinCompileCommon

// metadata issue, still has issue in third party compiled libraries :(
// https://youtrack.jetbrains.com/issue/KT-66568/w-KLIB-resolver-The-same-uniquename...-found-in-more-than-one-library
class MetadataPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target.pluginManager) {
            apply(KotlinMultiplatformPluginWrapper::class)
        }

        target.extensions.getByType<KotlinMultiplatformExtension>().apply {
            metadata {
                compilations.all {
                    val compilationName = name
                    compileTaskProvider.configure {
                        if (this is KotlinCompileCommon) {
                            moduleName = "${project.path}:${project.group}:$compilationName"
                        }
                    }
                }
            }
        }
    }
}

package com.multiplatform.td.conventions

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginWrapper

class AfterKtlintPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {

        plugins.withType<KotlinMultiplatformPluginWrapper> {
            extensions.getByType(KotlinMultiplatformExtension::class).apply {
                val disabledTargets = targets.filter { it.publishable.not() }

                if (disabledTargets.isNotEmpty()) {
                    println("${target.name}: ${disabledTargets.joinToString { it.name }}")
                }
            }
        }

        dependencies {}
    }
}

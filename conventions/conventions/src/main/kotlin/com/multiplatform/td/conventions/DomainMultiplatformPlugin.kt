package com.multiplatform.td.conventions

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class DomainMultiplatformPlugin: Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(CommonMultiplatformPlugin::class)
            apply(LanguageLintPlugin::class)
        }

        extensions.getByType<KotlinMultiplatformExtension>().apply {
            // just print for now
            val disabledTargets = targets.filter { it.publishable.not() }.toList()
            println("${target.name}/disabled -> ${disabledTargets.joinToString { it.name }}")
        }

        dependencies {}
    }
}

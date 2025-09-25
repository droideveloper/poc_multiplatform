package com.multiplatform.td.conventions

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

class DomainMultiplatformPlugin: Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(CommonMultiplatformPlugin::class)
            apply(LanguageLintPlugin::class)
        }
    }
}

package com.multiplatform.td.conventions

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.KtlintPlugin

class LanguageLintPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(KtlintPlugin::class)
        }

        configure<KtlintExtension> {
            version.set(ktlintCliVersion.asString())
            verbose.set(true)
            outputToConsole.set(true)
            enableExperimentalRules.set(true)
            ignoreFailures.set(true)
            filter {
                exclude { element ->
                    val path = element.file.path
                    path.contains("\\generated\\")
                        || path.contains("/generated/")
                        || path.contains("\\buildkonfig\\")
                        || path.contains("/buildkonfig/")
                }
            }
        }

        dependencies {}
    }
}

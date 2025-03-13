package com.multiplatform.td.conventions

import com.codingfeline.buildkonfig.compiler.FieldSpec
import com.codingfeline.buildkonfig.gradle.BuildKonfigExtension
import com.codingfeline.buildkonfig.gradle.BuildKonfigPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginWrapper

class BuildKonfigExtendingPlugin: Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(KotlinMultiplatformPluginWrapper::class)
            apply(BuildKonfigPlugin::class)
        }

        val extension = extensions.getByType<BuildKonfigExtension>()
        extension.defaultConfigs {
            buildConfigField(FieldSpec.Type.STRING, "KONFIG_FLAVOR", "default")
        }
        flavors.keys.forEach { flavor ->
            extension.defaultConfigs(flavor) {
                buildConfigField(FieldSpec.Type.STRING, "KONFIG_FLAVOR", flavor)
            }
        }
    }
}

package com.multiplatform.td.conventions

import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.variant.KotlinMultiplatformAndroidComponentsExtension
import dev.mokkery.gradle.MokkeryGradlePlugin
import dev.mokkery.gradle.mokkery
import kotlinx.kover.gradle.plugin.dsl.KoverProjectExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginWrapper

class MokkeryMultiplatformPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(KotlinMultiplatformPluginWrapper::class)
            apply(MokkeryGradlePlugin::class)
            apply(MultiplatformKoverPlugin::class)
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

        val isFlavorApplied = extensions.findByType<KotlinMultiplatformAndroidComponentsExtension>() != null

        extensions.getByType<KoverProjectExtension>().apply {
            //currentProject {
            //    createVariant("custom") {
            //        val variantName = when {
            //            isFlavorApplied -> "mockDebug"
            //            else -> "jvm"
            //        }
            //        add(variantName)
            //    }
            //}
        }

        dependencies {}
    }
}

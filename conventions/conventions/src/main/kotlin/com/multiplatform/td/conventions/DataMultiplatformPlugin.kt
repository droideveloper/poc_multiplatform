package com.multiplatform.td.conventions

import de.jensklingenberg.ktorfit.gradle.KtorfitGradlePlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlinx.serialization.gradle.SerializationGradleSubplugin

class DataMultiplatformPlugin: Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(MultiplatformPlugin::class)
            apply(KspMultiplatformPlugin::class)
            apply(LanguageLintPlugin::class)
            apply(SerializationGradleSubplugin::class)
            apply(KtorfitGradlePlugin::class)
            apply(MetadataPlugin::class)
        }

        extensions.getByType<KotlinMultiplatformExtension>().apply {
            sourceSets.commonMain.configure {
                dependencies {
                    implementation(kotlinCoroutinesCore.asDependency())
                    implementation(kotlinSerializationJson.asDependency())

                    implementation(kotlinDatetime.asDependency())

                    implementation(ktorClientCore.asDependency())

                    implementation(ktorClientContentNegotiation.asDependency())
                    implementation(ktorSerializationKotlinxJson.asDependency())

                    implementation(ktorClientLogging.asDependency())

                    implementation(ktorfitLibLight.asDependency())
                }
            }
            sourceSets.jvmMain.configure {
                dependencies {
                    implementation(ktorClientOkhttp.asDependency())
                }
            }
            sourceSets.iosMain.configure {
                dependencies {
                    implementation(ktorClientDarwin.asDependency())
                }
            }

            val disabledTargets = targets.filter { it.publishable.not() }.toList()
            println("${target.name}/disabled -> ${disabledTargets.joinToString { it.name }}")
        }

        dependencies {
            configureKspDependencies()
        }

        configureKspTargetMetadataDependencies()
    }
}



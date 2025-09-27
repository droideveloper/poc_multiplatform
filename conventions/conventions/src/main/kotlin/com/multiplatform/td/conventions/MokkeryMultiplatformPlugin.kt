package com.multiplatform.td.conventions

import dev.mokkery.gradle.MokkeryGradlePlugin
import dev.mokkery.gradle.mokkery
import kotlinx.kover.gradle.plugin.KoverGradlePlugin
import kotlinx.kover.gradle.plugin.dsl.AggregationType
import kotlinx.kover.gradle.plugin.dsl.CoverageUnit
import kotlinx.kover.gradle.plugin.dsl.GroupingEntityType
import kotlinx.kover.gradle.plugin.dsl.KoverProjectExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginWrapper

class MokkeryMultiplatformPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(KotlinMultiplatformPluginWrapper::class)
            apply(MokkeryGradlePlugin::class)
            apply(KoverGradlePlugin::class)
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

        extensions.getByType<KoverProjectExtension>().apply {
            reports {
                filters {
                    excludes {
                        annotatedBy("androidx.compose.runtime.Composable")
                        packages("**.inject", "**.inject.binder", "**.generated.*", "**.testing")
                        classes("**.*Graph", "**.*Event")
                    }
                }
                total {
                    log {
                        onCheck.set(false)
                        header.set(target.name)
                        groupBy.set(GroupingEntityType.APPLICATION)
                        aggregationForGroup.set(AggregationType.COVERED_PERCENTAGE)
                        format.set("<entity> line coverage: <value>%")
                        coverageUnits.set(CoverageUnit.LINE)
                    }
                }
            }
        }

        dependencies {}
    }
}

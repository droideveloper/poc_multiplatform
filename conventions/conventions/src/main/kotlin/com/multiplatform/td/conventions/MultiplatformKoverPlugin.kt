package com.multiplatform.td.conventions

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
import kotlin.apply

class MultiplatformKoverPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(KoverGradlePlugin::class)
        }

        extensions.getByType<KoverProjectExtension>().apply {
            reports {
                filters {
                    excludes {
                        annotatedBy(
                            "org.jetbrains.compose.ui.tooling.preview.Preview",
                            "com.multiplatform.td.core.ui.KoverIgnore",
                            "**.Database",
                            "**.Dao",
                        )
                        packages("**.inject", "**.inject.binder", "**.generated.*", "**.testing")
                        classes(
                            "**.*Graph*",
                            "**.*Event",
                            "**.Composable*",
                            "**.GeneratedViewModelModule",
                            "**.*ViewModelFactoryBinder",
                            "**.*Activity",
                            "**.*ActivityKt",
                            "**.*Application",
                            "**.Mock*",
                            "**.*Mock",
                            "**.*Dao*",
                            "**.*Database*",
                            "**.*Route",
                        )
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

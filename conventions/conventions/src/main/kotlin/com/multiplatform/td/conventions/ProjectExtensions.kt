package com.multiplatform.td.conventions

import com.google.devtools.ksp.gradle.KspAATask
import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinAndroidTarget

internal fun Project.configureKspDependencies() {
    extensions.getByType<KotlinMultiplatformExtension>().apply {
        val isAndroidTarget = targets.any { it is KotlinAndroidTarget }
        applyTargetKspDependencies(
            target = this@configureKspDependencies,
            targets = when {
                isAndroidTarget -> kotlinLibraryTargets()
                else -> kotlinJvmTargets()
            },
        )
    }
}

internal fun Project.configureKotlinInjectDependencies() {
    extensions.getByType<KotlinMultiplatformExtension>().apply {
        val isAndroidTarget = targets.any { it is KotlinAndroidTarget }
        applyTargetKotlinInjectDependencies(
            target = this@configureKotlinInjectDependencies,
            targets = when {
                isAndroidTarget -> kotlinLibraryTargets()
                else -> kotlinJvmTargets()
            },
        )
    }
}

internal fun Project.configureKotlinInjectCommonDependencies() {
    configureCommonMetadataCompiler {
        add("kspCommonMainMetadata", kotlinInjectCompilerKsp.asDependency())
    }
}

internal fun Project.configureContributeCommonDependencies() {
    configureCommonMetadataCompiler {
        add("kspCommonMainMetadata", project(":core:injection:compiler"))
    }
}

internal fun Project.configureCommonMetadataCompiler(
    configuration: DependencyHandlerScope.() -> Unit,
) {
    extensions.getByType<KotlinMultiplatformExtension>().apply {
        sourceSets.commonMain.configure {
            kotlin.srcDirs("${projectDir.path}/build/generated/ksp/metadata/commonMain/kotlin")
        }

        tasks.withType(KspAATask::class).configureEach {
            if (name != "kspCommonMainKotlinMetadata") {
                dependsOn("kspCommonMainKotlinMetadata")
            }
        }
    }

    dependencies {
        configuration(this)
    }
}

internal fun Project.configureKspTargetMetadataDependencies() {
    extensions.getByType<KotlinMultiplatformExtension>().apply {
        val isAndroidTarget = targets.any { it is KotlinAndroidTarget }
        applyTargetKspMetadataDependencies(
            target = this@configureKspTargetMetadataDependencies,
            taskNames = when {
                isAndroidTarget -> kotlinLibraryMetadataTargets()
                else -> kotlinJvmMetadataTargets()
            }
        )
    }
}


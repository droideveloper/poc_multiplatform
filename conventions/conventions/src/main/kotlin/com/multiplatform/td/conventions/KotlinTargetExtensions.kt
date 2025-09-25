package com.multiplatform.td.conventions

import org.jetbrains.kotlin.gradle.dsl.KotlinTargetContainerWithPresetFunctions

internal val uiTargets = when {
    buildRunningOnCiEnvironment() -> arrayOf("Android")
    else -> arrayOf(
                "Android",
                "IosArm64",
                "IosSimulatorArm64",
                "IosX64",
            )
}


internal val libraryTargets = when {
    buildRunningOnCiEnvironment() -> arrayOf("Jvm")
    else -> arrayOf(
                "Jvm",
                "IosArm64",
                "IosSimulatorArm64",
                "IosX64",
            )
}


internal fun KotlinTargetContainerWithPresetFunctions.kotlinLibraryMetadataTargets() =
    uiTargets.map {
        "kspKotlin$it"
    }

internal fun KotlinTargetContainerWithPresetFunctions.kotlinJvmMetadataTargets() =
    libraryTargets.map {
        "kspKotlin$it"
    }

internal fun KotlinTargetContainerWithPresetFunctions.kotlinLibraryTargets() =
    uiTargets.map {
        "ksp$it"
    }

internal fun KotlinTargetContainerWithPresetFunctions.kotlinJvmTargets() =
    libraryTargets.map {
        "ksp$it"
    }

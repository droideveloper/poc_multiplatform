package com.multiplatform.td.conventions

import org.jetbrains.kotlin.gradle.dsl.KotlinTargetContainerWithPresetFunctions

internal val uiTargets = arrayOf(
    "Android",
    "IosArm64",
    "IosSimulatorArm64",
    "IosX64",
)

internal val libraryTargets = arrayOf(
    "Jvm",
    "IosArm64",
    "IosSimulatorArm64",
    "IosX64",
)

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

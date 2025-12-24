package com.multiplatform.td.conventions

import org.gradle.internal.extensions.stdlib.capitalized
import org.jetbrains.kotlin.gradle.dsl.KotlinTargetContainerWithPresetFunctions
import org.jetbrains.kotlin.konan.target.HostManager

internal fun KotlinTargetContainerWithPresetFunctions.kotlinLibraryMetadataTargets() =
    buildIosKspTaskIfHostAvailable { targetName ->
        "kspKotlin$targetName"
    }
        .apply { add("kspKotlinAndroid") }

internal fun KotlinTargetContainerWithPresetFunctions.kotlinJvmMetadataTargets() =
    buildIosKspTaskIfHostAvailable { targetName ->
        "kspKotlin$targetName"
    }
        .apply { add("kspKotlinJvm") }

internal fun KotlinTargetContainerWithPresetFunctions.kotlinLibraryTargets() =
    buildIosKspTaskIfHostAvailable { targetName ->
        "ksp$targetName"
    }
        .apply { add("kspAndroid") }

internal fun KotlinTargetContainerWithPresetFunctions.kotlinJvmTargets() =
    buildIosKspTaskIfHostAvailable { targetName ->
        "ksp$targetName"
    }
        .apply { add("kspJvm") }

internal fun KotlinTargetContainerWithPresetFunctions.buildIosKspTaskIfHostAvailable(
    applyPrefix: (String) -> String,
): MutableList<String> =
    iosArches()
        .map { it() }
        .filter { HostManager.hostIsMac }
        .map { it.name.capitalized() }
        .map { applyPrefix(it) }
        .toMutableList()

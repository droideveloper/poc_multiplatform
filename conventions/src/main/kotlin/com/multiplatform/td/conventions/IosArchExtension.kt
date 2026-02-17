package com.multiplatform.td.conventions

import org.jetbrains.kotlin.gradle.dsl.KotlinTargetContainerWithPresetFunctions
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

internal typealias IosArch = () -> KotlinNativeTarget

internal fun KotlinTargetContainerWithPresetFunctions.iosTargets() = iosArches().forEach(IosArch::invoke)

internal fun KotlinTargetContainerWithPresetFunctions.iosArches(): List<IosArch> = listOf(
    this::iosArm64,
    this::iosSimulatorArm64,
    this::iosX64,
)

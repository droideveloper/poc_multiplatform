package com.multiplatform.td.conventions

import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.artifacts.VersionConstraint
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.getByType
import java.util.Optional

internal val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal val Project.coreDesugarLibrary
    get() = libs.findLibrary("android.desugar.jdk.libs")

// Versions
internal val Project.compileSdkVersion
    get() = libs.findVersion("android.compileSdk")

internal val Project.targetSdkVersion
    get() = libs.findVersion("android.targetSdk")

internal val Project.minSdkVersion
    get() = libs.findVersion("android.minSdk")

internal fun Optional<Provider<MinimalExternalModuleDependency>>.asDependency() =
    this.get()

internal fun Optional<VersionConstraint>.asInt() =
    this.get()
        .displayName
        .toInt()

// library dependencies
internal val Project.kotlinSerializationJson
    get() = libs.findLibrary("kotlin.serialization.json")
internal val Project.kotlinCoroutinesCore
    get() = libs.findLibrary("kotlin.coroutines.core")
internal val Project.kotlinDatetime
    get() = libs.findLibrary("kotlin.datetime")
internal val Project.androidxActivityCompose
    get() = libs.findLibrary("androidx.activity.compose")
internal val Project.androidxLifecycleViewModel
    get() = libs.findLibrary("androidx.lifecycle.viewmodel")
internal val Project.androidxLifecycleRuntimeCompose
    get() = libs.findLibrary("androidx.lifecycle.runtime.compose")

internal val Project.ktorfitLibLight
    get() = libs.findLibrary("ktorfit.lib.light")
internal val Project.ktorfitKsp
    get() = libs.findLibrary("ktorfit.ksp")

internal val Project.kotlinInjectRuntime
    get() = libs.findLibrary("kotlin.inject.runtime")
internal val Project.kotlinInjectCompilerKsp
    get() = libs.findLibrary("kotlin.inject.compiler.ksp")

internal val Project.roomRuntime
    get() = libs.findLibrary("room.runtime")
internal val Project.roomKsp
    get() = libs.findLibrary("room.compiler")

internal val Project.ktorClientCore
    get() = libs.findLibrary("ktor.client.core")
internal val Project.ktorClientDarwin
    get() = libs.findLibrary("ktor.client.darwin")
internal val Project.ktorClientOkhttp
    get() = libs.findLibrary("ktor.client.okhttp")
internal val Project.ktorClientContentNegotiation
    get() = libs.findLibrary("ktor.client.content.negotiation")
internal val Project.ktorSerializationKotlinxJson
    get() = libs.findLibrary("ktor.serialization.kotlinx.json")
internal val Project.ktorClientLogging
    get() = libs.findLibrary("ktor.client.logging")

internal val Project.kotlinTest
    get() = libs.findLibrary("kotlin.test")
internal val Project.kotlinTestJunit
    get() = libs.findLibrary("kotlin.test.junit")
internal val Project.kotlinCoroutinesTest
    get() = libs.findLibrary("kotlin.coroutines.test")
internal val Project.jUnit
    get() = libs.findLibrary("junit")
internal val Project.androidxTestJunit
    get() = libs.findLibrary("androidx.test.junit")
internal val Project.androidxTestJunit4
    get() = libs.findLibrary("androidx.test.junit4")
internal val Project.androidxTestManifest
    get() = libs.findLibrary("androidx.test.manifest")
internal val Project.espressoCore
    get() = libs.findLibrary("androidx.espresso.core")
internal val Project.robolectric
    get() = libs.findLibrary("robolectric")



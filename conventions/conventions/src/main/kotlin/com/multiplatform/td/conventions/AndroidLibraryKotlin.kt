package com.multiplatform.td.conventions

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryExtension
import com.android.build.api.variant.AndroidComponentsExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.internal.sharedruntime.codegen.sourceNameOfBinaryName
import org.jetbrains.kotlin.gradle.dsl.KotlinBaseExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.utils.`is`

internal fun CommonExtension<*,*,*,*,*,*>.configureAndroidLibrary(
    target: Project,
) {
    val isApplication = this is ApplicationExtension

    compileSdk = target.compileSdkVersion.asInt()

    defaultConfig {
        minSdk = target.minSdkVersion.asInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
        isCoreLibraryDesugaringEnabled = true
    }

    testOptions {
        if (isApplication.not()) {
            targetSdk = target.targetSdkVersion.asInt()
        }
        unitTests {
            isIncludeAndroidResources = target.isAndroidResourcesShouldIncluded()
            isReturnDefaultValues = true
        }
    }

    target.extensions.getByType<KotlinBaseExtension>().apply {
        jvmToolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }

    configureFlavors()

    target.dependencies {
        add("coreLibraryDesugaring", target.coreDesugarLibrary.asDependency())
        add("debugImplementation", target.androidxTestManifest.asDependency())

        add("androidTestImplementation", target.androidxTestJunit.asDependency())
        add("androidTestImplementation", target.androidxTestJunit4.asDependency())
        add("androidTestImplementation", target.espressoCore.asDependency())
        add("androidTestImplementation", target.jUnit.asDependency())
    }
}

internal fun AndroidComponentsExtension<KotlinMultiplatformAndroidLibraryExtension, *, *>.configureAndroidLibrary(
    target: Project,
    enabledAndroidResources: Boolean = false,
) {
    finalizeDsl { extension ->
        with(extension) {
            minSdk = target.minSdkVersion.asInt()
            compileSdk = target.compileSdkVersion.asInt()

            androidResources {
                enable = enabledAndroidResources
            }

            if (target.isCommonTestEnabled() || target.isAndroidUnitTestEnabled()) {
                withHostTestBuilder {}.configure {
                    isIncludeAndroidResources = target.isAndroidResourcesShouldIncluded()
                    isReturnDefaultValues = true
                }
            }

            if (target.isaAndroidInstrumentedTestEnabled()) {
                withDeviceTestBuilder {}.configure {
                    instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    execution = "ANDROIDX_TEST_ORCHESTRATOR"
                }
            }
        }
    }

    configureFlavors()

    target.extensions.getByType<KotlinBaseExtension>().apply {
        jvmToolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }

    target.extensions.getByType<KotlinMultiplatformExtension>().apply {
        sourceSets.androidMain.dependencies {
            implementation(target.androidxTestManifest.asDependency())
        }
        if (target.isAndroidUnitTestEnabled()) {
            sourceSets.create("androidHostTest") {
                dependencies {
                    implementation(target.androidxTestJunit.asDependency())
                    implementation(target.androidxTestJunit4.asDependency())
                    implementation(target.espressoCore.asDependency())
                    implementation(target.jUnit.asDependency())
                }
            }
        }
        if (target.isaAndroidInstrumentedTestEnabled()) {
            sourceSets.create("androidDeviceTest") {
                dependencies {
                    implementation(target.androidxTestJunit.asDependency())
                    implementation(target.androidxTestJunit4.asDependency())
                    implementation(target.espressoCore.asDependency())
                    implementation(target.jUnit.asDependency())
                }
            }
        }
    }

    target.dependencies {
        add("coreLibraryDesugaring", target.coreDesugarLibrary.asDependency())
    }
}

internal fun Project.isAndroidResourcesShouldIncluded(): Boolean =
    isCommonTestEnabled() ||
        isAndroidUnitTestEnabled() ||
            isaAndroidInstrumentedTestEnabled()

internal fun Project.isAndroidUnitTestEnabled(): Boolean =
    layout.projectDirectory.dir("src/androidUnitTest").asFile.exists() ||
        layout.projectDirectory.dir("src/androidHostTest").asFile.exists()

internal fun Project.isaAndroidInstrumentedTestEnabled(): Boolean =
    layout.projectDirectory.dir("src/androidInstrumentedTest").asFile.exists() ||
        layout.projectDirectory.dir("src/androidDeviceTest").asFile.exists()

internal fun Project.isCommonTestEnabled(): Boolean =
    layout.projectDirectory.dir("src/commonTest").asFile.exists()

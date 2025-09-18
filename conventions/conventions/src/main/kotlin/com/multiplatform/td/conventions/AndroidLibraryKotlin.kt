package com.multiplatform.td.conventions

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinBaseExtension

internal fun CommonExtension<*,*,*,*,*,*>.configureAndroidLibrary(
    target: Project
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
            languageVersion.set(JavaLanguageVersion.of(21))
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

internal fun Project.isAndroidResourcesShouldIncluded(): Boolean =
    layout.projectDirectory.dir("src/androidUnitTest").asFile.exists() ||
        layout.projectDirectory.dir("src/commonTest").asFile.exists()

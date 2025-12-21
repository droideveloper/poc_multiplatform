package com.multiplatform.td.conventions

import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinBaseExtension

internal fun ApplicationAndroidComponentsExtension.configureAndroidApplication(
    target: Project,
) {
    finalizeDsl { extension ->
        with(extension) {
            defaultConfig {
                targetSdk = target.targetSdkVersion.asInt()
                minSdk = target.minSdkVersion.asInt()
                compileSdk = target.compileSdkVersion.asInt()
                versionCode = 1
                versionName = "1.0.0"

                vectorDrawables {
                    useSupportLibrary = true
                }
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
                isCoreLibraryDesugaringEnabled = true
            }

            buildTypes {
                debug {
                    applicationIdSuffix = ".debug"
                    isMinifyEnabled = false
                }
                release {
                    isMinifyEnabled = true
                }
            }

            packaging {
                resources {
                    excludes += "/META-INF/{AL2.0,LGPL2.1}"
                    excludes += "/META-INF/*.kotlin_module"
                }
            }

            testOptions {
                animationsDisabled = true
                unitTests {
                    isIncludeAndroidResources = target.isAndroidResourcesShouldIncluded()
                    isReturnDefaultValues = true
                }
            }

            configureFlavors()
        }
    }

    target.extensions.getByType<KotlinBaseExtension>().apply {
        jvmToolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }

    target.dependencies {
        add("coreLibraryDesugaring", target.coreDesugarLibrary.asDependency())
        add("debugImplementation", target.androidxTestManifest.asDependency())

        add("androidTestImplementation", target.androidxTestJunit.asDependency())
        add("androidTestImplementation", target.androidxTestJunit4.asDependency())
        add("androidTestImplementation", target.espressoCore.asDependency())
        add("androidTestImplementation", target.jUnit.asDependency())
    }
}

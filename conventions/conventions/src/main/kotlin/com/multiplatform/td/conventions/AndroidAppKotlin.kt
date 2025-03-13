package com.multiplatform.td.conventions

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Project

internal fun ApplicationExtension.configureAndroidApplication(
    target: Project,
) {
    namespace = "com.multiplatform.td"

    defaultConfig {
        applicationId = "com.multiplatform.td"
        targetSdk = target.targetSdkVersion.asInt()
        versionCode = 1
        versionName = "1.0.0"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
        }
        release {
            isMinifyEnabled = true
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    testOptions {
        animationsDisabled = true
        unitTests {
            isIncludeAndroidResources = true
        }
    }


    configureAndroidLibrary(target)
}

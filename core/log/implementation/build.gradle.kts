import com.android.build.api.dsl.androidLibrary

plugins {
    alias(libs.plugins.td.multiplatform.library)
    alias(libs.plugins.td.multiplatform.kotlin.inject.common)
}

kotlin {
    androidLibrary {
        namespace = "com.multiplatform.td.core.log"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.log.gateway)

                implementation(projects.core.environment.gateway)
                implementation(projects.core.environment.implementation)

                implementation(projects.core.injection)
            }
        }
    }
}

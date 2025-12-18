import com.android.build.api.dsl.androidLibrary

plugins {
    alias(libs.plugins.td.multiplatform.library)
}

kotlin {
    androidLibrary {
        namespace = "com.multiplatform.td.core.mvi"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.coroutines)

                implementation(libs.androidx.lifecycle.viewmodel)
                implementation(libs.androidx.lifecycle.runtime.compose)

                implementation(libs.kotlin.datetime)
            }
        }
    }
}

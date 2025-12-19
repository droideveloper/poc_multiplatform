import com.android.build.api.dsl.androidLibrary

plugins {
    alias(libs.plugins.td.multiplatform.ui)
    alias(libs.plugins.td.multiplatform.kotlin.inject)
}

kotlin {
    androidLibrary {
        namespace = "com.multiplatform.td.core.app"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.coroutines)
                implementation(projects.core.injection)

                implementation(projects.core.environment.gateway)
                implementation(projects.core.environment.implementation)

                implementation(libs.androidx.lifecycle.runtime.compose)
                implementation(libs.androidx.lifecycle.viewmodel)
                implementation(libs.androidx.lifecycle.viewmodel.compose)
                implementation(libs.navigation.compose)
            }
        }
    }

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
}

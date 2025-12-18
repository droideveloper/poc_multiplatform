import com.android.build.api.dsl.androidLibrary

plugins {
    alias(libs.plugins.td.multiplatform.library)
    alias(libs.plugins.td.multiplatform.kotlin.inject.common)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    androidLibrary {
        namespace = "com.multiplatform.td.core.navigation"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.navigation.gateway)
                implementation(projects.core.injection)

                implementation(projects.core.app)

                implementation(libs.navigation.compose)
                implementation(compose.runtime)
            }
        }
    }

    compilerOptions {
        freeCompilerArgs.add("-Xconsistent-data-class-copy-visibility")
    }
}

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

                implementation(libs.bundles.androidx.lifecycle)

                implementation(libs.navigation.compose)
            }
        }
    }

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
}

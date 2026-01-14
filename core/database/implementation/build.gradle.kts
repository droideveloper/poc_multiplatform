plugins {
    alias(libs.plugins.td.multiplatform.library)
    alias(libs.plugins.td.multiplatform.room)
    alias(libs.plugins.td.multiplatform.kotlin.inject.common)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    androidLibrary {
        namespace = "com.multiiplatform.td.core.database"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.database.gateway)

                implementation(projects.core.coroutines)
                implementation(projects.core.injection)
                implementation(projects.core.app)

                implementation(projects.core.environment.gateway)
                implementation(projects.core.environment.implementation)

                implementation(libs.bundles.database)

                implementation(libs.compose.runtime)
            }
        }
    }

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
}

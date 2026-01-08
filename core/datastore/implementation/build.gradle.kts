plugins {
    alias(libs.plugins.td.multiplatform.library)
    alias(libs.plugins.td.multiplatform.kotlin.inject.common)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    androidLibrary {
        namespace = "com.multiplatform.td.core.datastore"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.datastore.gateway)

                implementation(projects.core.coroutines)
                implementation(projects.core.injection)
                implementation(projects.core.kotlin)

                implementation(projects.core.app)
                implementation(projects.core.environment.gateway)
                implementation(projects.core.environment.implementation)

                implementation(libs.kotlin.serialization.json)

                implementation(libs.bundles.datastore)

                implementation(compose.runtime)
            }
        }
    }

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
}

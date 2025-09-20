plugins {
    alias(libs.plugins.td.multiplatform.library)
    alias(libs.plugins.td.multiplatform.kotlin.inject.common)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
}

kotlin {
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

                implementation(libs.androidx.datastore.core)
                implementation(libs.androidx.datastore.preferences.core)
                implementation(libs.androidx.datastore.core.okio)

                implementation(compose.runtime)
            }
        }
    }

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
}

android {
    namespace = "com.multiplatform.td.core.datastore"
}

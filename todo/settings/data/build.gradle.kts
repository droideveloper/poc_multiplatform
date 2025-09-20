plugins {
    alias(libs.plugins.td.multiplatform.data)
    alias(libs.plugins.td.multiplatform.kotlin.inject.common)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.todo.settings.domain)

                implementation(projects.core.injection)
                implementation(projects.core.kotlin)
                implementation(projects.core.datastore.gateway)

                implementation(libs.androidx.datastore.preferences.core)
                implementation(libs.androidx.datastore.core.okio)

                implementation(libs.kotlin.inject.runtime)
                implementation(libs.kotlin.serialization.json)
                implementation(libs.kotlin.serialization.json.okio)
                implementation(libs.okio)

                implementation(libs.kotlin.datetime)
            }
        }
    }

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
}

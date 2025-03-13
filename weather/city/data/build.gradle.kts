plugins {
    alias(libs.plugins.td.multiplatform.data)
    alias(libs.plugins.td.multiplatform.room)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.weather.city.domain)

                implementation(projects.core.injection)
                implementation(projects.core.kotlin)

                implementation(projects.core.database.gateway)
                implementation(projects.core.datastore.gateway)
                implementation(projects.core.environment.gateway)

                implementation(libs.kotlin.inject.runtime)
                implementation(libs.kotlin.serialization.json)
                implementation(libs.kotlin.serialization.json.okio)
                implementation(libs.okio)
            }
        }
    }

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
}

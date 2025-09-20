plugins {
    alias(libs.plugins.td.multiplatform.ui)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.td.multiplatform.kotlin.inject)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.weather.city.data)
                implementation(projects.weather.city.domain)

                implementation(projects.weather.core.ui)
                implementation(projects.core.ui)

                implementation(projects.core.coroutines)

                implementation(projects.core.database.gateway)
                implementation(projects.core.database.implementation)

                implementation(projects.core.datastore.gateway)
                implementation(projects.core.datastore.implementation)

                implementation(projects.core.environment.gateway)
                implementation(projects.core.environment.implementation)

                implementation(projects.core.app)
                implementation(projects.core.injection)
                implementation(projects.core.kotlin)
                implementation(projects.core.mvi)

                implementation(compose.components.resources)

                implementation(libs.kotlin.inject.runtime)

                implementation(libs.kotlin.serialization.json.okio)
                implementation(libs.kotlin.serialization.json)
                implementation(libs.okio)

                implementation(libs.ktorfit.lib.light)

                implementation(libs.navigation.compose)

                implementation(libs.sqlite.bundled)
                implementation(libs.room.runtime)
            }
        }
    }

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
}

android {
    namespace = "com.multiplatform.weather.city"
}

plugins {
    alias(libs.plugins.td.multiplatform.domain)
    alias(libs.plugins.td.multiplatform.mokkery)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.weather.core.measure.gateway)

                implementation(projects.core.navigation.gateway)

                implementation(projects.core.coroutines)
                implementation(projects.core.kotlin)
                implementation(projects.core.injection)

                implementation(libs.kotlin.inject.runtime)
                implementation(libs.kotlin.serialization.json)
            }
        }
    }
}

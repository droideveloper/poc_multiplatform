plugins {
    alias(libs.plugins.td.multiplatform.domain)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.weather.city.domain)

                implementation(projects.weather.core.measure.gateway)

                implementation(projects.core.injection)
                implementation(projects.core.kotlin)
                implementation(projects.core.repository)
                implementation(projects.core.navigation.gateway)

                implementation(libs.kotlin.inject.runtime)
                implementation(libs.kotlin.serialization.json)
            }
        }
    }
}

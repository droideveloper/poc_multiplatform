plugins {
    alias(libs.plugins.td.multiplatform.domain)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.kotlin)
                implementation(projects.core.navigation.gateway)

                implementation(projects.core.injection)
                implementation(libs.kotlin.datetime)
                implementation(libs.kotlin.serialization.json)

                implementation(libs.kotlin.inject.runtime)
            }
        }
    }
}

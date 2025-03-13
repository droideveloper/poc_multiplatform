plugins {
    alias(libs.plugins.td.multiplatform.domain)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.kotlin)
                implementation(projects.core.injection)
                implementation(projects.core.datastore.gateway)

                implementation(libs.kotlin.inject.runtime)
                implementation(libs.kotlin.serialization.json)

                implementation(libs.okio)
            }
        }
    }
}

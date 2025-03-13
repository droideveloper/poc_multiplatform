plugins {
    alias(libs.plugins.td.multiplatform.common)
    alias(libs.plugins.td.multiplatform.kotlin.inject.common)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.kotlin)
                implementation(projects.core.injection)

                implementation(projects.core.environment.gateway)
                implementation(projects.core.log.gateway)

                implementation(libs.ktor.client.core)

                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)

                implementation(libs.ktor.client.logging)

                implementation(libs.ktorfit.lib.light)
            }
        }
    }
}

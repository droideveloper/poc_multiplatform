plugins {
    alias(libs.plugins.td.multiplatform.library)
    alias(libs.plugins.td.multiplatform.kotlin.inject.common)
}

kotlin {
    sourceSets {
        androidMain{
            dependencies {
                implementation(libs.ktor.client.okhttp)
            }
        }
        commonMain {
            dependencies {
                implementation(projects.core.network.gateway)

                implementation(projects.core.kotlin)
                implementation(projects.core.injection)

                implementation(projects.core.environment.gateway)
                implementation(projects.core.environment.implementation)

                implementation(projects.core.log.gateway)
                implementation(projects.core.log.implementation)

                implementation(libs.ktor.client.core)

                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)

                implementation(libs.ktor.client.logging)

                implementation(libs.ktorfit.lib.light)
            }
        }
        iosMain{
            dependencies {
                implementation(libs.ktor.client.darwin)
            }
        }
    }
}

android {
    namespace = "com.multiplatform.td.core.network"
}

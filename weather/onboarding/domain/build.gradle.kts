plugins {
    alias(libs.plugins.td.multiplatform.domain)
    alias(libs.plugins.td.multiplatform.mokkery)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.kotlin)
                implementation(projects.core.injection)

                implementation(projects.core.navigation.gateway)

                implementation(libs.kotlin.inject.runtime)
                implementation(libs.kotlin.serialization.json)
            }
        }
        commonTest {
            dependencies {
                implementation(projects.core.testing.gateway)
            }
        }
    }
}

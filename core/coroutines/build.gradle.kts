plugins {
    alias(libs.plugins.td.multiplatform.common)
    alias(libs.plugins.td.multiplatform.mokkery)
}

kotlin {
    sourceSets {
        commonTest {
            dependencies {
                implementation(projects.core.testing.gateway)
                implementation(libs.coroutines.turbine)
            }
        }
        commonMain {
            dependencies {
                implementation(projects.core.injection)
                implementation(libs.kotlin.coroutines.core)

                implementation(libs.kotlin.inject.runtime)

                implementation(libs.kotlin.datetime)
            }
        }
    }
}

plugins {
    alias(libs.plugins.td.multiplatform.common)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.kotlin)

                implementation(libs.kotlin.inject.runtime)
            }
        }
    }
}

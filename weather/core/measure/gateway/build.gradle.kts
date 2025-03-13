plugins {
    alias(libs.plugins.td.multiplatform.common)
    alias(libs.plugins.td.multiplatform.kotlin.inject.common)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.injection)
                implementation(projects.core.kotlin)
            }
        }
    }
}

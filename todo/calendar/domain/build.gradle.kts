plugins {
    alias(libs.plugins.td.multiplatform.domain)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.kotlin)
                implementation(projects.core.injection)

                implementation(libs.kotlin.inject.runtime)
                implementation(libs.kotlin.datetime)
            }
        }
    }
}

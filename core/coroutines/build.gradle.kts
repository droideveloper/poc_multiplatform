plugins {
    alias(libs.plugins.td.multiplatform.common)
}

kotlin {
    sourceSets {
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

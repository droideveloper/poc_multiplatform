plugins {
    alias(libs.plugins.td.multiplatform.common)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.coroutines)
            }
        }
    }
}

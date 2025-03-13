plugins {
    alias(libs.plugins.td.multiplatform.common)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.repository)
            implementation(projects.core.kotlin)

            implementation(libs.kotlin.inject.runtime)

            implementation(libs.androidx.datastore.preferences.core)
            implementation(libs.androidx.datastore.core.okio)
        }
    }
}

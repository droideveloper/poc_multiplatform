plugins {
    alias(libs.plugins.td.multiplatform.library)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.coroutines)

            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)

            implementation(libs.kotlin.datetime)
        }
    }
}

android {
    namespace = "com.multiplatform.td.core.mvi"
}

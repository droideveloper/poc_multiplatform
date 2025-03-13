plugins {
    alias(libs.plugins.td.multiplatform.ui)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(compose.animation)
            implementation(compose.material3)

            implementation(libs.compose.material.icons)
            implementation(libs.navigation.compose)
        }
    }
}

android {
    namespace = "com.multiplatform.td.core.ui"
}

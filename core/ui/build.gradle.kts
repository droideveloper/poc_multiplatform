plugins {
    alias(libs.plugins.td.multiplatform.ui)
}

kotlin {
    android {
        namespace = "com.multiplatform.td.core.ui"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.compose.animation)
                implementation(libs.compose.material3)

                implementation(libs.compose.material.icons)
                implementation(libs.navigation.compose)
            }
        }
    }
}

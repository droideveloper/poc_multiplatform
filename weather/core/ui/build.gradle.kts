plugins {
    alias(libs.plugins.td.multiplatform.ui)
}

kotlin {
    androidLibrary {
        namespace = "com.multiplatform.weather.core.ui"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.ui)
                implementation(projects.core.coroutines)

                implementation(libs.compose.animation)
                implementation(libs.compose.material3)

                implementation(libs.compose.material.icons)
            }
        }
    }
}

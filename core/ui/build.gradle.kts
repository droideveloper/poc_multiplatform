import com.android.build.api.dsl.androidLibrary

plugins {
    alias(libs.plugins.td.multiplatform.ui)
}

kotlin {
    androidLibrary {
        namespace = "com.multiplatform.td.core.ui"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(compose.animation)
                implementation(compose.material3)

                implementation(libs.compose.material.icons)
                implementation(libs.navigation.compose)
            }
        }
    }
}

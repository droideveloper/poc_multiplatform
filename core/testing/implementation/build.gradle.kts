plugins {
    alias(libs.plugins.td.multiplatform.ui)
}

kotlin {
    sourceSets {
        androidMain {
            dependencies {
                implementation(libs.androidx.test.manifest)
                implementation(libs.androidx.test.junit)
                implementation(libs.androidx.test.junit4)
                implementation(libs.androidx.test.junit4.android)
                implementation(libs.androidx.espresso.core)
                implementation(libs.junit)

                implementation(compose.uiTooling)
                implementation(compose.preview)

                implementation(libs.preview.scanner)

                implementation(libs.roborazzi.core)
                implementation(libs.roborazzi.compose)
                implementation(libs.roborazzi)

                implementation(libs.robolectric)
            }
        }
        commonMain {
            dependencies {
                api(projects.core.testing.gateway)

                api(libs.androidx.lifecycle.viewmodel)
                api(libs.androidx.lifecycle.runtime.compose)
            }
        }
    }
}

android {
    namespace = "com.multiplatform.td.core.testing"
    buildFeatures {
        compose = true
    }
}

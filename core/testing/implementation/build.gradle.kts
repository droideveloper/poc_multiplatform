plugins {
    alias(libs.plugins.td.multiplatform.ui)
}

kotlin {
    androidLibrary {
        namespace = "com.multiplatform.td.core.testing"
    }

    sourceSets {
        androidMain {
            dependencies {
                implementation(libs.bundles.ui.testing)

                implementation(compose.uiTooling)
                implementation(compose.preview)

                implementation(libs.preview.scanner)

                implementation(libs.bundles.roborazzi)

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

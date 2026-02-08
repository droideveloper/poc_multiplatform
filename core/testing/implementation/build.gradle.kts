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

                implementation(libs.compose.ui.tooling)
                implementation(libs.compose.preview)

                implementation(libs.classgraph)
                implementation(libs.kotlin.reflect)
                implementation(
                    fileTree("${rootDir.absolutePath}/conventions/libs") {
                        includes += "composable-preview-scanner/*.jar"
                    },
                )

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

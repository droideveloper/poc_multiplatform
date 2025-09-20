plugins {
    alias(libs.plugins.td.multiplatform.library)
    alias(libs.plugins.td.multiplatform.mokkery)
}

kotlin {
    sourceSets {
        androidMain {
            dependencies {
                implementation(libs.androidx.test.manifest)
                implementation(libs.androidx.test.junit)
                implementation(libs.androidx.test.junit4)
                implementation(libs.androidx.espresso.core)
                implementation(libs.junit)

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
}

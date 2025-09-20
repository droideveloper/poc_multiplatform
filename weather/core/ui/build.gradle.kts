plugins {
    alias(libs.plugins.td.multiplatform.ui)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.ui)
                implementation(projects.core.coroutines)

                implementation(compose.animation)
                implementation(compose.material3)
            }
        }
    }
}

android {
    namespace = "com.multiplatform.weather.core.ui"
}

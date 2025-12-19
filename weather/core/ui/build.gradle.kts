import com.android.build.api.dsl.androidLibrary

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

                implementation(compose.animation)
                implementation(compose.material3)

                implementation(libs.compose.material.icons)
            }
        }
    }
}

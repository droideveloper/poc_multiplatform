plugins {
    alias(libs.plugins.td.multiplatform.ui)
}

kotlin {
    android {
        namespace = "com.multiplatform.todo.core.ui"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.ui)
                implementation(projects.core.coroutines)

                implementation(libs.kotlin.datetime)

                implementation(libs.compose.animation)
                implementation(libs.compose.material3)
            }
        }
    }
}

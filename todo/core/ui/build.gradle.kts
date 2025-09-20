plugins {
    alias(libs.plugins.td.multiplatform.ui)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.ui)
                implementation(projects.core.coroutines)

                implementation(libs.kotlin.datetime)

                implementation(compose.animation)
                implementation(compose.material3)
            }
        }
    }
}

android {
    namespace = "com.multiplatform.todo.core.ui"
}

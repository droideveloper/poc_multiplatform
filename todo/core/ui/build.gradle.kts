import com.android.build.api.dsl.androidLibrary

plugins {
    alias(libs.plugins.td.multiplatform.ui)
}

kotlin {
    androidLibrary {
        namespace = "com.multiplatform.todo.core.ui"
    }

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

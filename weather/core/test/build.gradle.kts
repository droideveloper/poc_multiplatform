plugins {
    alias(libs.plugins.td.multiplatform.ui)
    alias(libs.plugins.td.multiplatform.kotlin.inject)
}

kotlin {
    android {
        namespace = "com.multiplatform.weather.core.test"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.app)
                implementation(projects.core.coroutines)
                implementation(projects.core.injection)

                implementation(projects.core.datastore.gateway)
                implementation(projects.core.datastore.implementation)
                implementation(projects.core.environment.gateway)
                implementation(projects.core.environment.implementation)
            }
        }
    }

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
}

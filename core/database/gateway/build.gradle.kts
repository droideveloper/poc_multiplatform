plugins {
    alias(libs.plugins.td.multiplatform.common)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.coroutines)
                implementation(projects.core.kotlin)

                implementation(projects.core.environment.gateway)

                implementation(libs.kotlin.inject.runtime)
                implementation(libs.room.runtime)
            }
        }
    }

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
}

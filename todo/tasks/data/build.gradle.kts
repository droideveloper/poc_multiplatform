plugins {
    alias(libs.plugins.td.multiplatform.data)
    alias(libs.plugins.td.multiplatform.room)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.todo.tasks.domain)

                implementation(projects.core.injection)
                implementation(projects.core.kotlin)

                implementation(projects.core.database.gateway)
                implementation(projects.core.environment.gateway)

                implementation(libs.kotlin.inject.runtime)

            }
        }
    }

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
}
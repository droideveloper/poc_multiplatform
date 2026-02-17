plugins {
    alias(libs.plugins.td.multiplatform.library)
    alias(libs.plugins.td.multiplatform.kotlin.inject.common)
    alias(libs.plugins.td.multiplatform.build.konfig)
}

internal val `package` = "com.multiplatform.td.core.environment"

kotlin {
    androidLibrary {
        namespace = `package`
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.environment.gateway)

                implementation(projects.core.injection)
            }
        }
    }

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
}

buildkonfig {
    packageName = `package`
}

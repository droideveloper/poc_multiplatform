plugins {
    alias(libs.plugins.td.multiplatform.library)
    alias(libs.plugins.td.multiplatform.kotlin.inject.common)
    alias(libs.plugins.td.build.konfig)
}

kotlin {
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

internal val `package` = "com.multiplatform.td.core.environment"

android {
    namespace = `package`

    buildFeatures {
        buildConfig = true
    }
}

buildkonfig {
    packageName = `package`
}

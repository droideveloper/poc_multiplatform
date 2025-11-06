plugins {
    alias(libs.plugins.td.multiplatform.common)
}

kotlin {
    sourceSets {
        jvmMain {
            dependencies {
                api(libs.kotlin.test.junit)
            }
        }
        commonMain {
            dependencies {
                api(libs.kotlin.test)
                api(libs.kotlin.coroutines.test)
            }
        }
    }
}

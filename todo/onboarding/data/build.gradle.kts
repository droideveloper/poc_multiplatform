plugins {
    alias(libs.plugins.td.multiplatform.data)
    alias(libs.plugins.td.multiplatform.kotlin.inject.common)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.todo.onboarding.domain)

                implementation(projects.core.coroutines)
                implementation(projects.core.kotlin)
                implementation(projects.core.injection)

                implementation(projects.core.datastore.gateway)

                implementation(libs.kotlin.inject.runtime)
            }
        }
    }
}
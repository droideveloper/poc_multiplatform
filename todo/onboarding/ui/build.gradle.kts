plugins {
    alias(libs.plugins.td.multiplatform.ui)
    alias(libs.plugins.td.multiplatform.kotlin.inject)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.todo.onboarding.data)
                implementation(projects.todo.onboarding.domain)

                implementation(projects.core.app)
                implementation(projects.core.coroutines)
                implementation(projects.core.kotlin)
                implementation(projects.core.injection)
                implementation(projects.core.mvi)

                implementation(projects.core.datastore.gateway)
                implementation(projects.core.datastore.implementation)
                implementation(projects.core.environment.gateway)
                implementation(projects.core.environment.implementation)
                implementation(projects.core.navigation.gateway)
                implementation(projects.core.navigation.implementation)

                implementation(libs.kotlin.inject.runtime)

                implementation(libs.navigation.compose)
            }
        }
    }
}

android {
    namespace = "com.multiplatform.todo.onboarding"
}

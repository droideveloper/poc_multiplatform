plugins {
    alias(libs.plugins.td.multiplatform.ui)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.td.multiplatform.kotlin.inject)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.todo.settings.data)
                implementation(projects.todo.settings.domain)

                implementation(projects.core.ui)
                implementation(projects.todo.core.ui)

                implementation(projects.core.app)
                implementation(projects.core.coroutines)
                implementation(projects.core.injection)
                implementation(projects.core.kotlin)
                implementation(projects.core.mvi)

                implementation(projects.core.datastore.gateway)
                implementation(projects.core.datastore.implementation)
                implementation(projects.core.environment.gateway)
                implementation(projects.core.environment.implementation)

                implementation(compose.components.resources)

                implementation(libs.kotlin.inject.runtime)
                implementation(libs.kotlin.serialization.json)
                implementation(libs.kotlin.datetime)

                implementation(libs.navigation.compose)
            }
        }
    }
}

dependencies {
    kspCommonMainMetadata(projects.core.injection.compiler)
}

android {
    namespace = "com.multiplatform.todo.settings"
}

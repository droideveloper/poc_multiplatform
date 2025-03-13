plugins {
    alias(libs.plugins.td.multiplatform.ui)
    alias(libs.plugins.td.multiplatform.kotlin.inject)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.todo.home.domain)

                implementation(projects.core.ui)
                implementation(projects.todo.core.ui)

                implementation(projects.todo.tasks.data)
                implementation(projects.todo.tasks.domain)
                implementation(projects.todo.tasks.ui)

                implementation(projects.todo.calendar.domain)
                implementation(projects.todo.calendar.ui)

                implementation(projects.todo.settings.data)
                implementation(projects.todo.settings.domain)
                implementation(projects.todo.settings.ui)

                implementation(projects.core.app)
                implementation(projects.core.coroutines)
                implementation(projects.core.injection)
                implementation(projects.core.kotlin)
                implementation(projects.core.mvi)

                implementation(projects.core.database.gateway)
                implementation(projects.core.database.implementation)
                implementation(projects.core.environment.gateway)
                implementation(projects.core.environment.implementation)
                implementation(projects.core.navigation.gateway)
                implementation(projects.core.navigation.implementation)

                implementation(compose.components.resources)

                implementation(libs.kotlin.inject.runtime)
                implementation(libs.kotlin.datetime)

                implementation(libs.kotlin.serialization.json)
                implementation(libs.navigation.compose)
            }
        }
    }
}

android {
    namespace = "com.multiplatform.todo.home"
}

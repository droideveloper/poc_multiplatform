plugins {
    alias(libs.plugins.td.multiplatform.app)
}

kotlin {
    iosTargets(
        named = "Todo",
        isShared = false,
    )

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.todo.calendar.ui)
                implementation(projects.todo.home.domain)
                implementation(projects.todo.home.ui)
                implementation(projects.todo.onboarding.ui)
                implementation(projects.todo.settings.ui)
                implementation(projects.todo.tasks.domain)
                implementation(projects.todo.tasks.ui)

                implementation(projects.core.ui)
                implementation(projects.todo.core.ui)

                implementation(projects.core.app)
                implementation(projects.core.injection)
                implementation(projects.core.kotlin)
                implementation(projects.core.coroutines)
                implementation(projects.core.mvi)

                implementation(projects.core.environment.gateway)
                implementation(projects.core.environment.implementation)
                implementation(projects.core.navigation.gateway)
                implementation(projects.core.navigation.implementation)
                implementation(projects.core.database.gateway)
                implementation(projects.core.database.implementation)
                implementation(projects.core.datastore.gateway)
                implementation(projects.core.datastore.implementation)
                implementation(projects.core.network.gateway)
                implementation(projects.core.network.implementation)
                implementation(projects.core.log.gateway)
                implementation(projects.core.log.implementation)

                implementation(libs.navigation.compose)
                implementation(libs.kotlin.datetime)
            }
        }
    }
}

android {
    namespace = "com.multiplatform.todo"
    defaultConfig {
        applicationId = "com.multiplatform.todo"
    }
}

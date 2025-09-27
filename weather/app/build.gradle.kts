plugins {
    alias(libs.plugins.td.multiplatform.app)
    alias(libs.plugins.td.kover)
}

kotlin {
    iosTargets(
        named = "Weather",
        isShared = false,
        options = mapOf(
            "bundleId" to "com.multiplatform.weather",
        ),
    )

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.weather.city.domain)
                implementation(projects.weather.city.ui)

                implementation(projects.weather.forecast.domain)
                implementation(projects.weather.forecast.ui)

                implementation(projects.weather.onboarding.domain)
                implementation(projects.weather.onboarding.ui)

                implementation(projects.weather.settings.domain)
                implementation(projects.weather.settings.ui)

                implementation(projects.weather.core.measure.gateway)
                implementation(projects.weather.core.ui)

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

                implementation(libs.kotlin.inject.runtime)
                implementation(libs.navigation.compose)
                implementation(libs.kotlin.datetime)
            }
        }
    }

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
}

kover {
    currentProject {
        createVariant("custom") {
            add("stagingDebug")
        }
    }
    dependencies {
        kover(projects.weather.onboarding.data)
        kover(projects.weather.onboarding.domain)
        kover(projects.weather.onboarding.ui)

        kover(projects.weather.settings.data)
        kover(projects.weather.settings.domain)
        kover(projects.weather.settings.ui)
    }
}

android {
    namespace = "com.multiplatform.weather"
    defaultConfig {
        applicationId = "com.multiplatform.weather"
    }
}

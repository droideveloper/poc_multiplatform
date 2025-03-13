plugins {
    alias(libs.plugins.td.multiplatform.data)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.weather.onboarding.domain)

                implementation(projects.weather.city.data)
                implementation(projects.weather.city.domain)

                implementation(projects.weather.settings.data)
                implementation(projects.weather.settings.domain)

                implementation(projects.core.kotlin)
                implementation(projects.core.injection)
                implementation(projects.core.datastore.gateway)

                implementation(libs.kotlin.inject.runtime)
            }
        }
    }
}

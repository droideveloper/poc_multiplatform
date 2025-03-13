plugins {
    alias(libs.plugins.td.multiplatform.data)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.weather.forecast.domain)

                implementation(projects.weather.city.data)
                implementation(projects.weather.city.domain)

                implementation(projects.weather.settings.data)
                implementation(projects.weather.settings.domain)

                implementation(projects.weather.core.measure.gateway)

                implementation(projects.core.injection)
                implementation(projects.core.kotlin)
                implementation(projects.core.repository)

                implementation(projects.core.datastore.gateway)
                implementation(projects.core.network.gateway)
                implementation(projects.core.environment.gateway)

                implementation(libs.kotlin.inject.runtime)
            }
        }
    }
}

import com.android.build.api.dsl.androidLibrary

plugins {
    alias(libs.plugins.td.multiplatform.ui)
    alias(libs.plugins.td.multiplatform.kotlin.inject)
    alias(libs.plugins.td.multiplatform.mokkery)
}

kotlin {
    androidLibrary {
        namespace = "com.multiplatform.weather.forecast"
    }

    sourceSets {
        getByName("androidHostTest") {
            dependencies {
                implementation(projects.core.testing.implementation)
            }
        }
        commonTest {
            dependencies {
                implementation(projects.core.testing.gateway)
            }
        }
        commonMain {
            dependencies {
                implementation(projects.weather.forecast.data)
                implementation(projects.weather.forecast.domain)

                implementation(projects.weather.city.data)
                implementation(projects.weather.city.domain)
                implementation(projects.weather.city.ui)

                implementation(projects.weather.settings.data)
                implementation(projects.weather.settings.domain)

                implementation(projects.weather.core.measure.gateway)
                implementation(projects.weather.core.ui)

                implementation(compose.components.resources)

                implementation(projects.core.app)
                implementation(projects.core.coroutines)
                implementation(projects.core.injection)
                implementation(projects.core.kotlin)
                implementation(projects.core.mvi)
                implementation(projects.core.ui)
                implementation(projects.core.repository)

                implementation(projects.core.navigation.gateway)
                implementation(projects.core.navigation.implementation)

                implementation(projects.core.network.gateway)
                implementation(projects.core.network.implementation)

                implementation(projects.core.environment.gateway)
                implementation(projects.core.environment.implementation)

                implementation(projects.core.log.gateway)
                implementation(projects.core.log.implementation)

                implementation(projects.core.datastore.gateway)
                implementation(projects.core.datastore.implementation)

                implementation(libs.kotlin.inject.runtime)
                implementation(libs.kotlin.serialization.json)
                implementation(libs.ktorfit.lib.light)
                implementation(libs.kotlin.datetime)
                implementation(libs.navigation.compose)
                implementation(libs.compose.material.icons)
            }
        }
    }
}

dependencies {
    kover(projects.weather.forecast.data)
    kover(projects.weather.forecast.domain)
}

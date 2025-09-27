plugins {
    alias(libs.plugins.td.multiplatform.ui)
    alias(libs.plugins.td.multiplatform.kotlin.inject)
    alias(libs.plugins.td.multiplatform.mokkery)
}

kotlin {
    sourceSets {
        androidUnitTest {
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
                implementation(projects.weather.settings.data)
                implementation(projects.weather.settings.domain)

                implementation(projects.weather.city.data)
                implementation(projects.weather.city.domain)
                implementation(projects.weather.city.ui)

                implementation(projects.core.ui)
                implementation(projects.weather.core.ui)

                implementation(projects.weather.core.measure.gateway)

                implementation(projects.core.app)
                implementation(projects.core.coroutines)
                implementation(projects.core.injection)
                implementation(projects.core.kotlin)
                implementation(projects.core.mvi)
                implementation(projects.core.repository)

                implementation(projects.core.datastore.gateway)
                implementation(projects.core.datastore.implementation)

                implementation(projects.core.database.gateway)
                implementation(projects.core.database.implementation)

                implementation(projects.core.navigation.gateway)
                implementation(projects.core.navigation.implementation)

                implementation(projects.core.environment.gateway)
                implementation(projects.core.environment.implementation)

                implementation(libs.kotlin.inject.runtime)

                implementation(libs.ktorfit.lib.light)

                implementation(libs.navigation.compose)
            }
        }
    }
}

kover {
    currentProject {
        createVariant("custom") {
            add("stagingDebug")
        }
    }
    dependencies {
        kover(projects.weather.settings.data)
        kover(projects.weather.settings.ui)
    }
}

android {
    namespace = "com.multiplatform.weather.settings"
}

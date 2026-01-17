plugins {
    alias(libs.plugins.td.multiplatform.app)
    alias(libs.plugins.td.kover)
    alias(libs.plugins.roborazzi)
}

android {
    namespace = "com.multiplatform.weather"
    defaultConfig {
        testInstrumentationRunnerArguments += mapOf("clearPackageData" to "true")
        applicationId = "com.multiplatform.weather"

        testBuildType = "debug"
        testInstrumentationRunner = "com.multiplatform.weather.WeatherTestRunner"
    }

    testOptions {
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
        unitTests {
            all {
                it.systemProperties["robolectric.pixelCopyRenderMode"] = "hardware"
            }
        }
    }
}

dependencies {
    implementation(projects.weather.app)

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
    implementation(projects.core.ui)

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

    implementation(libs.bundles.coil)
    implementation(libs.bundles.android.activity)

    testImplementation(libs.bundles.ui.testing)

    testImplementation(libs.compose.ui.tooling)
    testImplementation(libs.compose.preview)

    testImplementation(libs.preview.scanner)

    testImplementation(libs.bundles.roborazzi)

    testImplementation(projects.core.testing.implementation)

    androidTestImplementation(libs.bundles.instrumented.testing)

    androidTestImplementation(projects.weather.core.test)

    androidTestImplementation(libs.bundles.espresso)
    androidTestImplementation(libs.bundles.espresso.idling)

    androidTestUtil(libs.androidx.test.orchestrator)
}

kover {
    currentProject {
        createVariant("custom") {
            add("mockDebug", optional = true)
        }
    }
}

dependencies {
    kover(projects.weather.city.data)
    kover(projects.weather.city.domain)
    kover(projects.weather.city.ui)

    kover(projects.weather.onboarding.data)
    kover(projects.weather.onboarding.domain)
    kover(projects.weather.onboarding.ui)

    kover(projects.weather.settings.data)
    kover(projects.weather.settings.domain)
    kover(projects.weather.settings.ui)
}

roborazzi {
    outputDir.set(
        project.layout.projectDirectory.dir("src/test/snapshots"),
    )
}

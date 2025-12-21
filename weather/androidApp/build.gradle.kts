plugins {
    alias(libs.plugins.td.multiplatform.app)
    alias(libs.plugins.td.kover)
    alias(libs.plugins.roborazzi)
}

android {
    namespace = "com.multiplatform.weather"
    defaultConfig {
        applicationId = "com.multiplatform.weather"

        testBuildType = "debug"
        testInstrumentationRunner = "com.multiplatform.weather.WeatherTestRunner"
        testInstrumentationRunnerArguments += mapOf("clearPackageData" to "true")
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

    testImplementation(libs.androidx.test.manifest)
    testImplementation(libs.androidx.test.junit)
    testImplementation(libs.androidx.test.junit4)
    testImplementation(libs.androidx.test.junit4.android)
    testImplementation(libs.androidx.espresso.core)
    testImplementation(libs.junit)

    testImplementation(compose.uiTooling)
    testImplementation(compose.preview)

    testImplementation(libs.preview.scanner)
    testImplementation(projects.core.testing.implementation)

    testImplementation(libs.roborazzi.core)
    testImplementation(libs.roborazzi.compose)
    testImplementation(libs.roborazzi.junit.rule)
    testImplementation(libs.roborazzi)

    androidTestImplementation(libs.androidx.test.manifest)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.junit4)
    androidTestImplementation(libs.androidx.test.junit4.android)
    androidTestImplementation(libs.junit)

    androidTestImplementation(projects.weather.core.test)

    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.espresso.contrib)
    androidTestImplementation(libs.androidx.espresso.intents)
    androidTestImplementation(libs.androidx.espresso.accessibility)
    androidTestImplementation(libs.androidx.espresso.web)
    androidTestImplementation(libs.androidx.espresso.idling)
    androidTestImplementation(libs.androidx.espresso.idling.resources)

    androidTestUtil(libs.androidx.test.orchestrator)
}

kover {
    currentProject {
        createVariant("custom") {
            add("mockDebug")
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

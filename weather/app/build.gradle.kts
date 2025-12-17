plugins {
    alias(libs.plugins.td.multiplatform.app)
    alias(libs.plugins.td.kover)
    alias(libs.plugins.roborazzi)
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
        androidUnitTest {
            dependencies {
                implementation(libs.androidx.test.manifest)
                implementation(libs.androidx.test.junit)
                implementation(libs.androidx.test.junit4)
                implementation(libs.androidx.test.junit4.android)
                implementation(libs.androidx.espresso.core)
                implementation(libs.junit)

                implementation(compose.uiTooling)
                implementation(compose.preview)

                implementation(libs.preview.scanner)
                implementation(projects.core.testing.implementation)

                implementation(libs.roborazzi.core)
                implementation(libs.roborazzi.compose)
                implementation(libs.roborazzi.junit.rule)
                implementation(libs.roborazzi)
            }
        }
        androidInstrumentedTest {
            dependencies {
                implementation(libs.androidx.test.manifest)
                implementation(libs.androidx.test.runner)
                implementation(libs.androidx.test.rules)
                implementation(libs.androidx.test.junit)
                implementation(libs.androidx.test.junit4)
                implementation(libs.androidx.test.junit4.android)
                implementation(libs.junit)

                implementation(projects.weather.core.test)

                implementation(libs.androidx.espresso.core)
                implementation(libs.androidx.espresso.contrib)
                implementation(libs.androidx.espresso.intents)
                implementation(libs.androidx.espresso.accessibility)
                implementation(libs.androidx.espresso.web)
                implementation(libs.androidx.espresso.idling)
                implementation(libs.androidx.espresso.idling.resources)
            }
            // for orchestrator to work as expected it needs to be `androidTestUtil` type of dependency :)
            dependencies.add("androidTestUtil", libs.androidx.test.orchestrator)
        }
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

                implementation(projects.weather.core.test)
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
        project.layout.projectDirectory.dir("src/androidUnitTest/snapshots"),
    )
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

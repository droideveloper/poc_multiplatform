plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ksp)
}

dependencies {
    implementation(projects.core.injection)

    implementation(libs.ksp.api)

    implementation(libs.kotlin.poet)
    implementation(libs.kotlin.poet.ksp)

    implementation(libs.auto.service.annotations)
    ksp(libs.auto.service.ksp)

    implementation(libs.kotlin.inject.runtime)

    testImplementation(libs.kotlin.inject.runtime)
    testImplementation(projects.core.injection)
    testImplementation(libs.ksp.api)

    testImplementation(libs.bundles.kotlin.compile.testing)
    testImplementation(platform(libs.junit5.bom))
    testImplementation(libs.bundles.kotlin.test.junit5)

    testRuntimeOnly(libs.junit5.engine)
    testRuntimeOnly(libs.junit5.launcher)
}

tasks {
    test {
        useJUnitPlatform()
        jvmArgs(
           "--add-opens=java.base/java.lang=ALL-UNNAMED",
            "--add-opens=java.base/java.util=ALL-UNNAMED",
        )
    }
}

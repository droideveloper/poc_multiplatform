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
}
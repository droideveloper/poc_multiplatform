import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "com.multiplatform.td.conventions"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
    }
}

dependencies {
    implementation(libs.kotlin.multiplatform.plugin)
    implementation(libs.android.gradle.plugin)
    implementation(libs.android.tools.common.plugin)
    implementation(libs.compose.gradle.plugin)
    implementation(libs.compose.compiler.gradle.plugin)
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.kotlin.serialization.plugin)
    implementation(libs.ksp.gradle.plugin)
    implementation(libs.room.gradle.plugin)
    implementation(libs.ktorfit.gradle.plugin)
    implementation(libs.buildkonfig.gradle.plugin)
    implementation(libs.buildkonfig.compiler.gradle.plugin)
    implementation(libs.mokkery.gradle.plugin)
    implementation(libs.ktlint.gradle.plugin)
    implementation(libs.kover.gradle.plugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("TdMultiplatform") {
            id = "td.multiplatform"
            implementationClass = "com.multiplatform.td.conventions.MultiplatformPlugin"
        }
        register("TdMultiplatformDomain") {
            id = "td.multiplatform.domain"
            implementationClass = "com.multiplatform.td.conventions.DomainMultiplatformPlugin"
        }
        register("TdMultiplatformData") {
            id = "td.multiplatform.data"
            implementationClass = "com.multiplatform.td.conventions.DataMultiplatformPlugin"
        }
        register("TdMultiplatformLibrary") {
            id = "td.multiplatform.library"
            implementationClass = "com.multiplatform.td.conventions.LibraryMultiplatformPlugin"
        }
        register("TdMultiplatformUi") {
            id = "td.multiplatform.ui"
            implementationClass = "com.multiplatform.td.conventions.UiMultiplatformPlugin"
        }
        register("TdMultiplatformApp") {
            id = "td.multiplatform.app"
            implementationClass = "com.multiplatform.td.conventions.AndroidAppPlugin"
        }
        register("TdBuildKonfig") {
            id = "td.build.konfig"
            implementationClass = "com.multiplatform.td.conventions.BuildKonfigExtendingPlugin"
        }
        register("TdMultiplatformCommon") {
            id = "td.multiplatform.common"
            implementationClass = "com.multiplatform.td.conventions.CommonMultiplatformPlugin"
        }
        register("TdMultiplatformRoom") {
            id = "td.multiplatform.room"
            implementationClass = "com.multiplatform.td.conventions.RoomMultiplatformPlugin"
        }
        register("TdMultiplatformKsp") {
            id = "td.multiplatform.ksp"
            implementationClass = "com.multiplatform.td.conventions.KspMultiplatformPlugin"
        }
        register("TdMultiplatformMokkery") {
            id = "td.multiplatform.mokkery"
            implementationClass = "com.multiplatform.td.conventions.MokkeryMultiplatformPlugin"
        }
        register("TdMultiplatformKotlinInject") {
            id = "td.multiplatform.kotlin.inject"
            implementationClass = "com.multiplatform.td.conventions.KotlinInjectMultiplatformPlugin"
        }
        register("TdMultiplatformCommonKotlinInject") {
            id = "td.multiplatform.kotlin.inject.common"
            implementationClass = "com.multiplatform.td.conventions.KotlinInjectCommonMultiplatformPlugin"
        }
    }
}

import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
    alias(libs.plugins.ktlint)
}

group = "com.multiplatform.td.conventions"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

ktlint {
    version.set("1.7.1")
    enableExperimentalRules.set(true)
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
    }
}

dependencies {
    compileOnly(libs.kotlin.multiplatform.plugin)
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.android.tools.common.plugin)
    compileOnly(libs.multiplatform.android.library.plugin)
    compileOnly(libs.compose.gradle.plugin)
    compileOnly(libs.compose.compiler.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.kotlin.serialization.plugin)
    compileOnly(libs.ksp.gradle.plugin)
    compileOnly(libs.room.gradle.plugin)
    compileOnly(libs.ktorfit.gradle.plugin)
    compileOnly(libs.buildkonfig.gradle.plugin)
    compileOnly(libs.buildkonfig.compiler.gradle.plugin)
    compileOnly(libs.mokkery.gradle.plugin)
    compileOnly(libs.ktlint.gradle.plugin)
    compileOnly(libs.kover.gradle.plugin)
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
        register("TdMultiplatformBuildKonfig") {
            id = "td.multiplatform.build.konfig"
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
        register("TdMultiplatformKover") {
            id = "td.multiplatform.kover"
            implementationClass = "com.multiplatform.td.conventions.MultiplatformKoverPlugin"
        }
    }
}

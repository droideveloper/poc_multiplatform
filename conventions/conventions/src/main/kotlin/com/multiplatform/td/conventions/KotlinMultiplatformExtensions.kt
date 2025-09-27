package com.multiplatform.td.conventions

import androidx.room.gradle.RoomExtension
import de.jensklingenberg.ktorfit.gradle.KtorfitPluginExtension
import org.gradle.api.Project
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.compose.ComposePlugin
import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree
import org.jlleitschuh.gradle.ktlint.tasks.BaseKtLintCheckTask

internal fun KotlinMultiplatformExtension.applyAndroidCompose(
    compose: ComposePlugin.Dependencies,
    project: Project,
) {
    with(project) {
        sourceSets.androidMain.configure {
            dependencies {
                implementation(compose.preview)
                implementation(androidxActivityCompose.asDependency())
            }
        }
    }
}

internal fun KotlinMultiplatformExtension.applyCommonCompose(
    compose: ComposePlugin.Dependencies,
    project: Project,
) {
    with(project) {
        sourceSets.commonMain.configure {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                implementation(androidxLifecycleViewModel.asDependency())
                implementation(androidxLifecycleRuntimeCompose.asDependency())
            }
        }
        sourceSets.commonTest.configure {
            dependencies {
                @OptIn(ExperimentalComposeLibrary::class)
                implementation(compose.uiTest)
            }
        }
        sourceSets.androidUnitTest.configure {
            dependencies {
                implementation(jUnit.asDependency())
                implementation(espressoCore.asDependency())
                implementation(androidxTestJunit.asDependency())
                implementation(androidxTestJunit4.asDependency())
                implementation(androidxTestManifest.asDependency())
                implementation(robolectric.asDependency())
            }
        }
    }
}

internal fun applyTargetKspDependencies(
    target: Project,
    targets: List<String>,
) = with(target)
{
    val isKtorfitAvailable = extensions.findByType<KtorfitPluginExtension>() != null
    val isRoomAvailable = extensions.findByType<RoomExtension>() != null

    dependencies {
        targets.forEach {
            if (isKtorfitAvailable) add(it, ktorfitKsp.asDependency())
            if (isRoomAvailable) add(it, roomKsp.asDependency())
        }
    }
}

internal fun applyTargetKotlinInjectDependencies(
    target: Project,
    targets: List<String>,
) = with(target)
{
    dependencies {
        targets.forEach {
            add(it, kotlinInjectCompilerKsp.asDependency())
        }
    }
}

internal fun applyTargetKspMetadataDependencies(
    target: Project,
    taskNames: List<String>,
) {
    // Fix KSP task dependencies (https://github.com/google/ksp/issues/963)
    target.afterEvaluate {
        taskNames.forEach { taskName ->
            val task = target.tasks.find { it.name == taskName }
            task?.dependsOn("kspCommonMainKotlinMetadata")
        }

        tasks.withType(BaseKtLintCheckTask::class).configureEach {
            dependsOn("kspCommonMainKotlinMetadata")
        }
    }
}

internal fun KotlinMultiplatformExtension.configureMultiplatformJvm() {
    configureMultiplatformDefaults {
        jvm {
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_17)
            }
        }
    }
}

internal fun KotlinMultiplatformExtension.configureMultiplatformDefaults(
    config: KotlinMultiplatformExtension.() -> Unit,
) {
    applyDefaultHierarchyTemplate()
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
    config(this)
    iosTargets()
}

internal fun KotlinMultiplatformExtension.configureMultiplatformLibrary() {
    configureMultiplatformDefaults {
        androidTarget {
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_11)
            }

            // means commonMainTest written for composable will be executed on device or emulator, which is expensive way to test
            @OptIn(ExperimentalKotlinGradlePluginApi::class)
            instrumentedTestVariant.sourceSetTree.set(KotlinSourceSetTree.test)
        }
    }
}

internal fun KotlinMultiplatformExtension.iosTargets(
    named: String,
    isShared: Boolean = true,
    options: Map<String, String> = emptyMap()
) {
    iosArches().forEach { target ->
        target().binaries.framework {
            baseName = named
            isStatic = isShared.not()
            options.forEach { opt ->
                binaryOption(opt.key, opt.value)
            }
        }
    }
}

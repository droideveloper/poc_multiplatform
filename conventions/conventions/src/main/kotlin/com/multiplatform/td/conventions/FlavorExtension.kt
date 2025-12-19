package com.multiplatform.td.conventions

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.ApplicationProductFlavor
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryExtension
import com.android.build.api.dsl.ProductFlavor
import com.android.build.api.variant.AndroidComponentsExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.support.uppercaseFirstChar

internal val flavors = mapOf(
    "mock" to ".mock",
    "staging" to ".staging",
    "prod" to null,
)

internal fun CommonExtension<*, *, *, *, *, *>.configureFlavors() {
    flavorDimensions += "default"
    productFlavors {
        flavors.forEach { (name, suffix) ->
            create(name) {
                dimension = "default"
                applySuffixIfNeeded(this, suffix)
            }
        }
    }
}

internal fun CommonExtension<*, *, *, *, *, *>.applySuffixIfNeeded(
    productFlavor: ProductFlavor,
    suffix: String? = null,
) {
    val isApplication = this is ApplicationExtension
    if (suffix != null) {
        if (isApplication && productFlavor is ApplicationProductFlavor) {
            productFlavor.applicationIdSuffix = suffix
        }
    }
}

internal fun AndroidComponentsExtension<KotlinMultiplatformAndroidLibraryExtension, *, *>.configureFlavors() {
    finalizeDsl { extension ->
        with(extension) {
            localDependencySelection {
                val buildTypes = listOf("debug", "release")
                selectBuildTypeFrom.set(buildTypes)
                flavors.forEach { (name, _) ->
                    productFlavorDimension(name) {
                        selectFrom.set(
                            buildTypes.map { "$name${it.uppercaseFirstChar()}" },
                        )
                    }
                }
            }
        }
    }
}

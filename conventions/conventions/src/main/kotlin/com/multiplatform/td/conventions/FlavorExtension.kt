package com.multiplatform.td.conventions

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.ApplicationProductFlavor
import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryExtension
import com.android.build.api.dsl.ProductFlavor
import com.android.build.api.variant.AndroidComponentsExtension
import org.gradle.kotlin.dsl.support.uppercaseFirstChar

internal val flavors = mapOf(
    "mock" to ".mock",
    "staging" to ".staging",
    "prod" to null,
)

internal fun ApplicationExtension.configureFlavors() {
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

internal fun applySuffixIfNeeded(
    productFlavor: ProductFlavor,
    suffix: String? = null,
) {
    if (suffix != null && productFlavor is ApplicationProductFlavor) {
        productFlavor.applicationIdSuffix = suffix
    }
}

internal fun AndroidComponentsExtension<KotlinMultiplatformAndroidLibraryExtension, *, *>.configureFlavorsLibrary() {
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

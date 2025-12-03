@file:OptIn(ExperimentalRoborazziApi::class)

package com.multiplatform.td.core.testing.screenshot

import com.github.takahirom.roborazzi.ExperimentalRoborazziApi
import com.github.takahirom.roborazzi.RoborazziComposeOptions
import com.github.takahirom.roborazzi.background
import com.github.takahirom.roborazzi.locale
import com.github.takahirom.roborazzi.size
import sergio.sastre.composable.preview.scanner.common.CommonPreviewInfo
import sergio.sastre.composable.preview.scanner.core.preview.ComposablePreview

object RoborazziComposeOptionsMapper {

    fun createFor(
        preview: ComposablePreview<CommonPreviewInfo>,
    ): RoborazziComposeOptions =
        RoborazziComposeOptions.Builder().apply {
            val info = preview.previewInfo
            size(
                widthDp = info.widthDp,
                heightDp = info.heightDp,
            )
            background(
                showBackground = info.showBackground,
                backgroundColor = info.backgroundColor,
            )
            locale(
                locale = info.locale.ifEmpty { "en" },
            )
        }
            .build()
}

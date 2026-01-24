@file:OptIn(ExperimentalRoborazziApi::class)

package com.multiplatform.weather

import android.os.Build
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.ExperimentalRoborazziApi
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.RoborazziRule
import com.multiplatform.td.core.testing.screenshot.AbstractScreenshotTest
import com.multiplatform.td.core.testing.screenshot.RoborazziOptionsMapper
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.ParameterizedRobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import sergio.sastre.composable.preview.scanner.android.AndroidComposablePreviewScanner
import sergio.sastre.composable.preview.scanner.android.AndroidPreviewInfo
import sergio.sastre.composable.preview.scanner.android.screenshotid.AndroidPreviewScreenshotIdBuilder
import sergio.sastre.composable.preview.scanner.core.preview.ComposablePreview
import java.io.File

@Config(
    sdk = [
        Build.VERSION_CODES.TIRAMISU,
    ],
    qualifiers = RobolectricDeviceQualifiers.Pixel5,
)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@RunWith(ParameterizedRobolectricTestRunner::class)
class WeatherScreenshotTest(
    private val suite: ScreenshotTestSuite,
) : AbstractScreenshotTest() {

    data class ScreenshotTestSuite(
        val preview: ComposablePreview<AndroidPreviewInfo>,
        val name: String,
    ) {

        override fun toString(): String = name
    }

    companion object {

        private val previews: List<ComposablePreview<AndroidPreviewInfo>> by lazy {
            AndroidComposablePreviewScanner()
                .scanPackageTrees(
                    "com.multiplatform.weather",
                )
                .includePrivatePreviews()
                .getPreviews()
        }

        @JvmStatic
        @ParameterizedRobolectricTestRunner.Parameters(name = "{0}")
        fun values(): List<ScreenshotTestSuite> = previews.map {
            ScreenshotTestSuite(preview = it, name = it.methodName)
        }
    }

    private fun screenshotNameFor(preview: ComposablePreview<AndroidPreviewInfo>): String =
        "${AndroidPreviewScreenshotIdBuilder(preview).build()}.png"

    @get:Rule
    val roborazziRule = RoborazziRule(
        composeRule = testRule,
        captureRoot = testRule.onRoot(),
        options = RoborazziRule.Options(
            captureType = RoborazziRule.CaptureType.LastImage(),
            roborazziOptions = RoborazziOptionsMapper.createFor(),
            outputFileProvider = { _, dir, _ ->
                File(dir, screenshotNameFor(suite.preview))
            },
        ),
    )

    @Test
    fun snapshot() {
        with(testRule) {
            setScreen { suite.preview() }
        }
    }
}

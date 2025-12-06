package com.multiplatform.td.core.testing

import android.os.Build
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.multiplatform.td.core.testing.screenshot.AbstractScreenshotTest
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@Config(
    sdk = [
        Build.VERSION_CODES.M,
        // Build.VERSION_CODES.TIRAMISU,
    ],
    qualifiers = RobolectricDeviceQualifiers.Pixel5,
)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
abstract class AbstractAndroidUnitTest : AbstractScreenshotTest()

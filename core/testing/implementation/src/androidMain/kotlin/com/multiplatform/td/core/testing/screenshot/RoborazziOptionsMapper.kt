package com.multiplatform.td.core.testing.screenshot

import com.github.takahirom.roborazzi.RoborazziOptions
import com.github.takahirom.roborazzi.ThresholdValidator

object RoborazziOptionsMapper {

    private const val DefaultMaxDifferencePercentage = 0.01f

    fun createFor(
        maxDifferencePercentage: Float = DefaultMaxDifferencePercentage,
    ): RoborazziOptions =
        RoborazziOptions(
            compareOptions = RoborazziOptions.CompareOptions(
                resultValidator = ThresholdValidator(maxDifferencePercentage),
            ),
        )
}

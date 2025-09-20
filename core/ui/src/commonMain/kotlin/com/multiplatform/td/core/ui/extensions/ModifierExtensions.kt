package com.multiplatform.td.core.ui.extensions

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp

fun Modifier.ignoreHorizontalPadding(horizontal: Dp) = layout { measurable, constraints ->
    val overriddenWidth = constraints.maxWidth + 2 * horizontal.roundToPx()
    val overriddenMeasure = measurable.measure(
        constraints = constraints.copy(maxWidth = overriddenWidth),
    )
    layout(overriddenMeasure.width, overriddenMeasure.height) {
        overriddenMeasure.place(0, 0)
    }
}

fun Modifier.ignoreStartPadding(start: Dp) = layout { measurable, constraints ->
    val overriddenWidth = constraints.maxWidth + start.roundToPx()
    val overriddenMeasure = measurable.measure(
        constraints = constraints.copy(maxWidth = overriddenWidth),
    )
    layout(overriddenMeasure.width, overriddenMeasure.height) {
        overriddenMeasure.place(0, 0)
    }
}

fun Modifier.ignoreEndPadding(end: Dp) = layout { measurable, constraints ->
    val overriddenWidth = constraints.maxWidth + end.roundToPx()
    val overriddenMeasure = measurable.measure(
        constraints = constraints.copy(maxWidth = overriddenWidth),
    )
    layout(overriddenMeasure.width, overriddenMeasure.height) {
        overriddenMeasure.place(end.roundToPx(), 0)
    }
}

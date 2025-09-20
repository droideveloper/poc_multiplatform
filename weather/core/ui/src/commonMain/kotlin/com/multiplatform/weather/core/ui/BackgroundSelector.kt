package com.multiplatform.weather.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush

@Composable
fun selectDayBackground(): Brush = Brush.verticalGradient(
    colors = listOf(
        FwTheme.colors.whites.secondary,
        FwTheme.colors.blues.light,
    ),
)

@Composable
fun selectDayBackgroundReversed(): Brush = Brush.verticalGradient(
    colors = listOf(
        FwTheme.colors.blues.light,
        FwTheme.colors.whites.secondary,
    ),
)

@Composable
fun selectNightBackground(): Brush = Brush.verticalGradient(
    colors = listOf(
        FwTheme.colors.blues.secondary,
        FwTheme.colors.blues.primary,
    ),
)

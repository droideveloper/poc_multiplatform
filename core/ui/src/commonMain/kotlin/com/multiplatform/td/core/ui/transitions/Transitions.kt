package com.multiplatform.td.core.ui.transitions

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavBackStackEntry

internal val WeirdEasing: Easing = Easing { fraction ->
    val t = fraction - 1f
    t * t * t * t * t + 1f
}

fun AnimatedContentTransitionScope<NavBackStackEntry>.slideInTransition() = slideIntoContainer(
    AnimatedContentTransitionScope.SlideDirection.Start,
    animationSpec = tween(easing = FastOutSlowInEasing, durationMillis = 300),
    initialOffset = { it },
)

fun AnimatedContentTransitionScope<NavBackStackEntry>.slideOutTransition() = slideOutOfContainer(
    AnimatedContentTransitionScope.SlideDirection.End,
    animationSpec = tween(easing = FastOutSlowInEasing, durationMillis = 300),
    targetOffset = { it },
)

fun AnimatedContentTransitionScope<NavBackStackEntry>.fadeOutTransition() =
    fadeOut(
        targetAlpha = 0.5f,
        animationSpec = tween(easing = WeirdEasing, durationMillis = 300),
    )

fun AnimatedContentTransitionScope<NavBackStackEntry>.fadeInTransition() =
    fadeIn(
        initialAlpha = 0.5f,
        animationSpec = tween(easing = WeirdEasing, durationMillis = 300),
    )

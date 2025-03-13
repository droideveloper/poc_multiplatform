package com.multiplatform.td.core.ui.extensions

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.multiplatform.td.core.ui.transitions.fadeInTransition
import com.multiplatform.td.core.ui.transitions.fadeOutTransition
import com.multiplatform.td.core.ui.transitions.slideInTransition
import com.multiplatform.td.core.ui.transitions.slideOutTransition

inline fun <reified T: Any> NavGraphBuilder.animatedComposable(
    crossinline content: @Composable (AnimatedContentScope.(NavBackStackEntry) -> Unit)
) {
    composable<T>(
        enterTransition = { slideInTransition() },
        exitTransition = { fadeOutTransition() },
        popEnterTransition = { fadeInTransition() },
        popExitTransition = { slideOutTransition() },
    ) {
        content(it)
    }
}

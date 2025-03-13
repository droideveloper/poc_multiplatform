package com.multiplatform.weather.onboarding.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.multiplatform.td.core.app.viewmodel.kotlinInjectViewModel
import com.multiplatform.td.core.ui.effects.OnScreenStart
import com.multiplatform.weather.core.ui.selectDayBackground
import com.multiplatform.weather.onboarding.city.rememberOnboardingComponent

@Composable
internal fun SplashScreen() {
    val component = rememberOnboardingComponent()
    val viewModel = kotlinInjectViewModel(
        create = component.splashViewModelFactory,
    )
    SplashUi(dispatch = viewModel::dispatch)
}

@Composable
internal fun SplashUi(
    dispatch: (SplashEvent) -> Unit,
) {
    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(selectDayBackground())
                .padding(it)
        )
    }
    OnScreenStart { dispatch(SplashEvent.OnScreenViewed) }
}
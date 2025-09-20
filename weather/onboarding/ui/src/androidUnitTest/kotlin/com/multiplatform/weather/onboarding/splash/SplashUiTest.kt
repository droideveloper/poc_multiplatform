package com.multiplatform.weather.onboarding.splash

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.multiplatform.td.core.testing.AbstractAndroidUnitTest
import com.multiplatform.weather.core.ui.FwTheme
import dev.mokkery.spy
import dev.mokkery.verify
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class SplashUiTest : AbstractAndroidUnitTest() {

    @Test
    fun testSplashUi() {
        with(testRule) {
            val dispatch: (SplashEvent) -> Unit = spy({})
            setScreen { SplashUi(dispatch) }

            verify { dispatch(SplashEvent.OnScreenViewed) }
        }
    }

    private fun ComposeTestRule.setScreen(content: @Composable () -> Unit) {
        if (this is ComposeContentTestRule) {
            setContent {
                FwTheme {
                    content()
                }
            }
        }
    }
}

package com.multiplatform.td.core.app.composable

import android.app.Application
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.multiplatform.td.core.app.AppComponent
import com.multiplatform.td.core.app.DefaultAppComponent
import com.multiplatform.td.core.app.error.CompositionContextException
import com.multiplatform.td.core.app.inject.ComponentStore
import com.multiplatform.td.core.testing.AbstractAndroidUnitTest
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertTrue

@RunWith(AndroidJUnit4::class)
internal class AppComponentContextTest : AbstractAndroidUnitTest() {

    @Test
    fun testAppComponent() {
        with(testRule) {
            setScreen {
                val context = LocalContext.current
                val component = remember { DefaultAppComponent(context.applicationContext as Application) }
                val navController = rememberNavController()
                AppContext(
                    component = component,
                    navHostController = navController,
                ) {
                    assertEquals(component, LocalAppComponent.current)
                    assertEquals(navController, LocalNavController.current)
                    assertEquals(component.componentStore, LocalComponentStore.current)
                }
            }
        }
    }

    @Test
    fun testNoAppComponentError() {
        with(testRule) {
            setScreen {
                val error = assertFails { LocalAppComponent.current }
                assertTrue { error is CompositionContextException }
                assertEquals(
                    "compositionLocalOf { ${AppComponent::class.simpleName} } not provided, please provide value for it.",
                    error.message,
                )
            }
        }
    }

    @Test
    fun testNoNavControllerError() {
        with(testRule) {
            setScreen {
                val error = assertFails { LocalNavController.current }
                assertTrue { error is CompositionContextException }
                assertEquals(
                    "compositionLocalOf { ${NavHostController::class.simpleName} } not provided, please provide value for it.",
                    error.message,
                )
            }
        }
    }

    @Test
    fun testNoComponentStoreError() {
        with(testRule) {
            setScreen {
                val error = assertFails { LocalComponentStore.current }
                assertTrue { error is CompositionContextException }
                assertEquals(
                    "compositionLocalOf { ${ComponentStore::class.simpleName} } not provided, please provide value for it.",
                    error.message,
                )
            }
        }
    }
}

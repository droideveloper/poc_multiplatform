package com.multiplatform.td.core.navigation.composable

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.multiplatform.td.core.app.composable.LocalComponentStore
import com.multiplatform.td.core.app.composable.LocalNavController
import com.multiplatform.td.core.app.error.CompositionContextException
import com.multiplatform.td.core.app.inject.ComponentStore
import com.multiplatform.td.core.app.inject.ComponentStoreImpl
import com.multiplatform.td.core.testing.AbstractAndroidUnitTest
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertTrue

@RunWith(AndroidJUnit4::class)
internal class NavigationContextTest : AbstractAndroidUnitTest() {

    @Test
    fun testNavigationContext() {
        with(testRule) {
            setScreen {
                val componentStore = remember { ComponentStoreImpl() }
                val navController = rememberNavController()
                CompositionLocalProvider(
                    LocalComponentStore provides componentStore,
                    LocalNavController provides navController,
                ) {
                    NavigationContext {
                        assertEquals(navController, LocalNavController.current)
                        assertEquals(componentStore, LocalComponentStore.current)
                        assertTrue {
                            val navComponent = LocalNavigationComponent.current
                            navComponent.navHostController == navController
                        }
                    }
                }
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

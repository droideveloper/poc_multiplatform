package com.multiplatform.td.core.testing

import android.content.ContentProvider
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Before
import org.junit.Rule
import org.robolectric.Robolectric

abstract class AbstractAndroidUnitTest {

    @get:Rule
    val testRule: ComposeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setup() { setupAndroidContextProvider() }

    private fun setupAndroidContextProvider() {
        val providerClass = androidContentProviderClass() ?: return
        Robolectric.setupContentProvider(providerClass)
    }

    private fun androidContentProviderClass(): Class<ContentProvider>? {
        val providerClassName = "org.jetbrains.compose.resources.AndroidContextProvider"
        return try {
            @Suppress("UNCHECKED_CAST")
            Class.forName(providerClassName) as Class<ContentProvider>
        } catch (_: ClassNotFoundException) {
            null
        }
    }
}
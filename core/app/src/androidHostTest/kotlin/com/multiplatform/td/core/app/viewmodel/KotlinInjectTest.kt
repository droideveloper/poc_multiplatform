package com.multiplatform.td.core.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.multiplatform.td.core.app.DefaultViewModel
import com.multiplatform.td.core.app.DefaultViewModelStoreOwner
import com.multiplatform.td.core.app.ParameterizedViewModel
import com.multiplatform.td.core.app.ViewModelStoreContext
import com.multiplatform.td.core.testing.AbstractAndroidUnitTest
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@RunWith(AndroidJUnit4::class)
internal class KotlinInjectTest : AbstractAndroidUnitTest() {

    @Test
    fun testKotlinInjectViewModel() {
        with(testRule) {
            setScreen {
                ViewModelStoreContext(
                    viewModelStoreOwner = DefaultViewModelStoreOwner(),
                ) {
                    val factory: () -> ViewModel = { DefaultViewModel() }
                    val viewModel: ViewModel = kotlinInjectViewModel(create = factory)
                    assertTrue { viewModel is DefaultViewModel }
                }
            }
        }
    }

    @Test
    fun testKotlinInjectParameterizedViewModel() {
        with(testRule) {
            setScreen {
                ViewModelStoreContext(
                    viewModelStoreOwner = DefaultViewModelStoreOwner(),
                ) {
                    val factory : (String) -> ViewModel = { param -> ParameterizedViewModel(param) }
                    val viewModel = kotlinInjectViewModel(param = "parameterized-view-model", create = factory)
                    assertTrue { viewModel is ParameterizedViewModel }
                    viewModel as ParameterizedViewModel
                    assertEquals("parameterized-view-model", viewModel.param)
                }
            }
        }
    }
}

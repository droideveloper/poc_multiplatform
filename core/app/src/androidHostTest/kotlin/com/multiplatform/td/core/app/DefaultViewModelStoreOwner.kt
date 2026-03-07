package com.multiplatform.td.core.app

import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

internal class DefaultViewModelStoreOwner : ViewModelStoreOwner {

    override val viewModelStore: ViewModelStore = ViewModelStore()
}

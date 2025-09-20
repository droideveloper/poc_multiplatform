@file:Suppress("UNCHECKED_CAST")

package com.multiplatform.td.core.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import kotlin.reflect.KClass

class ParameterizedViewModelFactory<VM : ViewModel, P>(
    private val param: P,
    private val create: (param: P) -> VM,
) : ViewModelProvider.Factory {

    override fun <VM : ViewModel> create(modelClass: KClass<VM>, extras: CreationExtras): VM {
        return create(param) as VM
    }
}

class ViewModelFactory<VM : ViewModel>(
    private val create: () -> VM,
) : ViewModelProvider.Factory {

    override fun <VM : ViewModel> create(modelClass: KClass<VM>, extras: CreationExtras): VM {
        return create() as VM
    }
}

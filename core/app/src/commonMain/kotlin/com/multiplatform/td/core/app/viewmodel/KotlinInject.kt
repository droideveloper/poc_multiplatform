package com.multiplatform.td.core.app.viewmodel

import androidx.compose.runtime.Composable
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner

@Composable
inline fun <reified VM : ViewModel> kotlinInjectViewModel(
    viewModelStoreOwner: ViewModelStoreOwner = LocalViewModelStoreOwner.current ?: error("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"),
    key: String? = null,
    extras: CreationExtras = defaultExtras(viewModelStoreOwner),
    noinline create: () -> VM,
): VM {
    return resolveViewModel(
        factory = ViewModelFactory(create = create),
        viewModelStore = viewModelStoreOwner.viewModelStore,
        key = key,
        extras = extras,
    )
}

@Composable
inline fun <reified VM : ViewModel, reified P> kotlinInjectViewModel(
    viewModelStoreOwner: ViewModelStoreOwner = LocalViewModelStoreOwner.current ?: error("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"),
    key: String? = null,
    extras: CreationExtras = defaultExtras(viewModelStoreOwner),
    param: P,
    noinline create: (param: P) -> VM,
): VM {
    val factory = ParameterizedViewModelFactory(param, create)
    return resolveViewModel(
        factory = factory,
        viewModelStore = viewModelStoreOwner.viewModelStore,
        key = key,
        extras = extras,
    )
}

inline fun <reified VM : ViewModel> resolveViewModel(
    factory: ViewModelProvider.Factory,
    viewModelStore: ViewModelStore,
    key: String? = null,
    extras: CreationExtras,
): VM {
    val provider = ViewModelProvider.create(viewModelStore, factory, extras)
    return when {
        key != null -> provider[key, VM::class]
        else -> provider[VM::class]
    }
}

fun defaultExtras(viewModelStoreOwner: ViewModelStoreOwner): CreationExtras = when {
    viewModelStoreOwner is HasDefaultViewModelProviderFactory -> viewModelStoreOwner.defaultViewModelCreationExtras
    else -> CreationExtras.Empty
}

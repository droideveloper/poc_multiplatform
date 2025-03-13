package com.multiplatform.td.core.mvi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.multiplatform.td.core.coroutines.throttle
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

abstract class MviViewModel<Action, State>(
    initialState: State,
) : ViewModel() {

    protected val actions = MutableSharedFlow<Action>()

    var state by mutableStateOf(initialState)
        protected set

    fun dispatch(action: Action) = viewModelScope.launch {
        actions.emit(action)
    }

    protected inline fun <reified ActionImpl : Action> on(
        crossinline block: suspend ActionImpl.() -> Unit,
    ) {
        actions
            .filterIsInstance<ActionImpl>()
            .map { runCatching { block(it) } }
            .map { it.exceptionOrNull() }
            .filterNotNull()
            .onEach(onError())
            .launchIn(viewModelScope)
    }

    protected inline fun <reified ActionImpl : Action> onClick(
        crossinline handle: suspend (event: ActionImpl) -> Unit,
    ) {
        actions
            .filterIsInstance<ActionImpl>()
            .throttle(Config.defaultDelay)
            .map { runCatching { handle(it) } }
            .map { it.exceptionOrNull() }
            .filterNotNull()
            .onEach(onError())
            .launchIn(viewModelScope)
    }

    open fun onError(): Throwable.() -> Unit = { }
}

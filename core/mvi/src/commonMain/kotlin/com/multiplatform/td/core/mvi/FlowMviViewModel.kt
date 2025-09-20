package com.multiplatform.td.core.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.multiplatform.td.core.coroutines.throttle
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

abstract class FlowMviViewModel<Action, State>(
    defaultState: State,
) : ViewModel() {

    val actions = MutableSharedFlow<Action>()

    protected val internalState = MutableStateFlow(defaultState)
    val state: StateFlow<State> get() = internalState

    fun dispatch(action: Action) = viewModelScope.launch {
        actions.emit(action)
    }

    protected inline fun <reified ActionImpl : Action> on(
        crossinline handle: suspend (event: ActionImpl) -> Unit,
    ) {
        actions
            .filterIsInstance<ActionImpl>()
            .map { runCatching { handle(it) } }
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

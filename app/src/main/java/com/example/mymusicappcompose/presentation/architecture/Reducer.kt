package com.example.mymusicappcompose.presentation.architecture

import com.example.mymusicappcompose.BuildConfig
import com.example.mymusicappcompose.presentation.architecture.history.History
import com.example.mymusicappcompose.presentation.architecture.history.StateHistory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class Reducer<STATE : UiState, EVENT : UiEvent>(state: STATE) {

    private val _state: MutableStateFlow<STATE> = MutableStateFlow(state)
    val state: StateFlow<STATE>
        get() = _state

    val history: History<STATE> = StateHistory { storedState -> _state.tryEmit(storedState) }

    init { history.addState(state) }

    fun sendEvent(event: EVENT) { reduce(_state.value, event) }

    fun setState(newState: STATE) {
        val success = _state.tryEmit(newState)

        if (BuildConfig.DEBUG && success) {
            history.addState(newState)
        }
    }

    abstract fun reduce(oldState: STATE, event: EVENT)
}

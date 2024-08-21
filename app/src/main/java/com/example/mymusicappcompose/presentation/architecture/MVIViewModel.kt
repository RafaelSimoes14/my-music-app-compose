package com.example.mymusicappcompose.presentation.architecture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymusicappcompose.presentation.architecture.history.History
import com.example.mymusicappcompose.log.logInfo
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class MVIViewModel<STATE : UiState, EVENT : UiEvent, EFFECT : UiEffect, INTENT : UiIntent> : ViewModel() {

    protected abstract fun getInitial(): STATE
    protected abstract fun onIntent(intent: INTENT)
    protected abstract fun onReduce(oldState: STATE, event: EVENT): STATE

    private val reducer: Reducer<STATE, EVENT> by lazy {
        object : Reducer<STATE, EVENT>(getInitial()) {
            override fun reduce(oldState: STATE, event: EVENT) {
                setState(this@MVIViewModel.onReduce(oldState, event))
            }
        }
    }

    val state: StateFlow<STATE>
        get() = reducer.state

    val history: History<STATE>
        get() = reducer.history

    private val _events = Channel<EVENT>(Channel.BUFFERED)
    private val events = _events.receiveAsFlow()

    private val _intents: MutableSharedFlow<INTENT> = MutableSharedFlow()
    private val intents = _intents.asSharedFlow()

    private val _effects: MutableSharedFlow<EFFECT> = MutableSharedFlow()
    val effects = _effects.asSharedFlow()

    init {
        subscribeIntents()
        subscribeEvents()

        state.onEach { logInfo("State: $it") }.launchIn(viewModelScope)
        intents.onEach { logInfo("Intent: $it") }.launchIn(viewModelScope)
        effects.onEach { logInfo("Effect: $it") }.launchIn(viewModelScope)
    }

    private fun subscribeEvents() {
        viewModelScope.launch {
            events.collect { reducer.sendEvent(it) }
        }
    }

    private fun subscribeIntents() {
        viewModelScope.launch {
            intents.collect { onIntent(it) }
        }
    }

    fun setEvent(builder: () -> EVENT) {
        val event = builder()
        viewModelScope.launch {
            val delivered = _events.trySend(event).isSuccess
            if (!delivered) {
                error("Missed event $event!")
            }
        }
    }

    protected fun setEffect(builder: () -> EFFECT) {
        val effect = builder()
        viewModelScope.launch { _effects.emit(effect) }
    }

    fun setIntent(builder: () -> INTENT) {
        val intent = builder()
        viewModelScope.launch { _intents.emit(intent) }
    }
}

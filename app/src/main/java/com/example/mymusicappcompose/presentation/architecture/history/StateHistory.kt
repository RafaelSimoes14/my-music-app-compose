package com.example.mymusicappcompose.presentation.architecture.history

import com.example.mymusicappcompose.presentation.architecture.UiState

class StateHistory<STATE : UiState>(private val onStateSelected: (STATE) -> Unit) : History<STATE> {
    private val states = mutableListOf<STATE>()

    override fun addState(state: STATE) { states.add(state) }
    override fun selectState(position: Int) { onStateSelected(states[position]) }
    override fun getStates(): List<STATE> { return states }
}

package com.example.mymusicappcompose.presentation.architecture.history

import com.example.mymusicappcompose.presentation.architecture.UiState

interface History<STATE : UiState> {
    fun addState(state: STATE)
    fun selectState(position: Int)
    fun getStates(): List<STATE>
}

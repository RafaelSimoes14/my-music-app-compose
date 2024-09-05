package com.example.mymusicappcompose.presentation.menu

import androidx.compose.runtime.Immutable
import com.example.mymusicappcompose.presentation.architecture.UiEffect
import com.example.mymusicappcompose.presentation.architecture.UiEvent
import com.example.mymusicappcompose.presentation.architecture.UiIntent
import com.example.mymusicappcompose.presentation.architecture.UiState

object Menu {

    @Immutable
    sealed interface Intent : UiIntent {
        data object OnGenres : Intent
        data object SignOut : Intent
        data object ConfirmError : Intent
        data object OnSearch : Intent
    }

    @Immutable
    data class State(
        val isLoading: Boolean,
        val error: Throwable?,

        ) : UiState {
        companion object {
            fun initial() = State(
                isLoading = false,
                error = null
            )
        }
    }

    @Immutable
    sealed interface Effect : UiEffect {
        data object OnGenres : Effect
        data object OnLogout : Effect
        data object OnSearch : Effect
    }

    @Immutable
    sealed interface Event : UiEvent {
        data class SetError(val throwable: Throwable) : Event
        data object Loading : Event
    }
}
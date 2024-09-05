package com.example.mymusicappcompose.presentation.splash

import androidx.compose.runtime.Immutable
import com.example.mymusicappcompose.presentation.architecture.UiEffect
import com.example.mymusicappcompose.presentation.architecture.UiEvent
import com.example.mymusicappcompose.presentation.architecture.UiIntent
import com.example.mymusicappcompose.presentation.architecture.UiState

object Splash {
    @Immutable
    sealed interface Intent : UiIntent {
        data object ConfirmError : Intent
    }

    @Immutable
    data class State(
        val error: Throwable?,
        val isLoading: Boolean
    ) : UiState {
        companion object {
            fun initial() = State(
                error = null,
                isLoading = true
            )
        }
    }

    @Immutable
    sealed interface Effect : UiEffect {
        data object OnShowMenu : Effect
        data object OnShowWelcome : Effect
    }

    @Immutable
    sealed interface Event : UiEvent {
        data class Error(val throwable: Throwable) : Event
        data class Loading(val value: Boolean) : Event
    }
}
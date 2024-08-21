package com.example.mymusicappcompose.presentation.signin

import androidx.compose.runtime.Immutable
import com.example.mymusicappcompose.presentation.architecture.UiEffect
import com.example.mymusicappcompose.presentation.architecture.UiEvent
import com.example.mymusicappcompose.presentation.architecture.UiIntent
import com.example.mymusicappcompose.presentation.architecture.UiState

object SignIn {

    @Immutable
    sealed interface Intent : UiIntent {
        data object Submit : Intent
        data object Back : Intent
        data object ConfirmError : Intent
    }

    @Immutable
    data class State(
        val email: String,
        val password: String,
        val validEmail: Boolean,
        val validPassword: Boolean,
        val isLoading: Boolean,
        val isSuccess: Boolean,
        val error: Throwable?
    ) : UiState {

        fun isButtonEnabled() = validEmail && validPassword

        companion object {
            fun initial() = State(
                email = "",
                password = "",
                validEmail = false,
                validPassword = false,
                isLoading = false,
                isSuccess = false,
                null
            )
        }
    }

    @Immutable
    sealed interface Effect : UiEffect {
        data object OnSuccess : Effect
        data object OnBack : Effect

        data class OnError(val throwable: Throwable) : Effect
    }

    @Immutable
    sealed interface Event : UiEvent {
        data class UpdateEmail(val email: String) : Event
        data class UpdatePassword(val password: String) : Event
        data class Error(val throwable: Throwable) : Event
        data class ValidEmail(val valid: Boolean) : Event
        data class ValidPassword(val valid: Boolean) : Event

        data object Loading : Event
        data object Success : Event
        data object Clear : Event
    }
}

package com.example.mymusicappcompose.presentation.signup

import androidx.compose.runtime.Immutable
import com.example.mymusicappcompose.presentation.architecture.UiEffect
import com.example.mymusicappcompose.presentation.architecture.UiEvent
import com.example.mymusicappcompose.presentation.architecture.UiIntent
import com.example.mymusicappcompose.presentation.architecture.UiState

object SignUp {

    @Immutable
    sealed interface Intent : UiIntent {
        data object Register : Intent
        data object Back : Intent
        data object ConfirmError : Intent
        data object Terms : Intent
    }

    @Immutable
    data class State(
        val email: String,
        val password: String,
        val confirmationPassword: String,
        val validEmail: Boolean,
        val passwordValidAndEqual: Boolean,
        val isLoading: Boolean,
        val isSuccess: Boolean,
        val error: Throwable?,
        val isChecked: Boolean
    ) : UiState {

        fun isButtonEnabled() = validEmail && passwordValidAndEqual && isChecked

        companion object {
            fun initial() = State(
                email = "",
                password = "",
                confirmationPassword = "",
                validEmail = false,
                passwordValidAndEqual = false,
                isLoading = false,
                isSuccess = false,
                error = null,
                isChecked = false
            )
        }
    }

    @Immutable
    sealed interface Effect : UiEffect {
        data object OnSuccess : Effect
        data object OnBack : Effect
        data object OnTerms : Effect

        data class OnError(val throwable: Throwable) : Effect
    }

    @Immutable
    sealed interface Event : UiEvent {
        data class UpdateEmail(val email: String) : Event
        data class UpdatePassword(val password: String) : Event
        data class UpdateConfirmPassword(val confirmationPassword: String) : Event
        data class Error(val throwable: Throwable) : Event
        data class UserCheck(val check: Boolean) : Event
        data class ValidEmail(val valid: Boolean) : Event
        data class ValidConfirmPassword(val valid: Boolean) : Event

        data object Loading : Event
        data object Success : Event
        data object Clear : Event
    }
}
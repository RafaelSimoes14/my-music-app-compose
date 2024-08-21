package com.example.mymusicappcompose.presentation.signup

import androidx.lifecycle.viewModelScope
import com.example.mymusicappcompose.core.Result
import com.example.mymusicappcompose.domain.user.UserUseCase
import com.example.mymusicappcompose.presentation.architecture.MVIViewModel
import com.example.mymusicappcompose.presentation.validator.EmailValidator
import com.example.mymusicappcompose.presentation.validator.PasswordValidator
import kotlinx.coroutines.launch

class SignUpViewModel : MVIViewModel<SignUp.State, SignUp.Event, SignUp.Effect, SignUp.Intent>() {
    private val useCase: UserUseCase = UserUseCase()
    override fun getInitial(): SignUp.State = SignUp.State.initial()

    override fun onReduce(oldState: SignUp.State, event: SignUp.Event): SignUp.State {
        return when (event) {
            is SignUp.Event.ValidEmail -> oldState.copy(validEmail = event.valid)
            is SignUp.Event.ValidConfirmPassword -> oldState.copy(passwordValidAndEqual = event.valid)
            is SignUp.Event.Loading -> oldState.copy(isLoading = true)
            is SignUp.Event.UpdateEmail -> {
                val valid = EmailValidator().isValid(event.email)
                setEvent { SignUp.Event.ValidEmail(valid) }

                oldState.copy(email = event.email)
            }
            is SignUp.Event.UpdatePassword -> {
                val valid  = PasswordValidator().isValidAndEqual(event.password, state.value.confirmationPassword)
                setEvent { SignUp.Event.ValidConfirmPassword(valid) }

                oldState.copy(password = event.password)
            }
            is SignUp.Event.UpdateConfirmPassword -> {
                val valid = PasswordValidator().isValidAndEqual(state.value.password, event.confirmationPassword)
                setEvent { SignUp.Event.ValidConfirmPassword(valid) }

                oldState.copy(confirmationPassword = event.confirmationPassword)
            }
            is SignUp.Event.Success -> oldState.copy(isLoading = false, isSuccess = true)
            is SignUp.Event.Error -> oldState.copy(isLoading = false, error = event.throwable)
            is SignUp.Event.Clear -> oldState.copy(
                error = null,
                email = "",
                password = "",
                confirmationPassword = ""
            )

            is SignUp.Event.UserCheck -> oldState.copy(isChecked = event.check)
        }
    }

    override fun onIntent(intent: SignUp.Intent) {
        when (intent) {
            is SignUp.Intent.Back -> setEffect { SignUp.Effect.OnBack }
            is SignUp.Intent.Register -> register()
            is SignUp.Intent.ConfirmError -> setEvent { SignUp.Event.Clear }
            is SignUp.Intent.Terms -> setEffect { SignUp.Effect.OnTerms }
        }
    }


    private fun register() {
        viewModelScope.launch {
            try {
                setEvent { SignUp.Event.Loading }
                when (val result = useCase.signUp(state.value.email, state.value.password)) {
                    is Result.Error -> setEvent { SignUp.Event.Error(result.error) }
                    is Result.Success -> {
                        setEvent { SignUp.Event.Success }
                    }
                }
            } catch (throwable: Throwable) {
                setEvent { SignUp.Event.Error(throwable) }
            }
        }

    }
}
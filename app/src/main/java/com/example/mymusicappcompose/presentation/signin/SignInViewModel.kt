package com.example.mymusicappcompose.presentation.signin

import androidx.lifecycle.viewModelScope
import com.example.mymusicappcompose.core.Result
import com.example.mymusicappcompose.domain.user.UserUseCase
import com.example.mymusicappcompose.presentation.architecture.MVIViewModel
import com.example.mymusicappcompose.presentation.validator.EmailValidator
import com.example.mymusicappcompose.presentation.validator.PasswordValidator
import kotlinx.coroutines.launch

class SignInViewModel : MVIViewModel<SignIn.State, SignIn.Event, SignIn.Effect, SignIn.Intent>() {

    private val useCase: UserUseCase = UserUseCase()

    override fun getInitial(): SignIn.State = SignIn.State.initial()

    override fun onReduce(oldState: SignIn.State, event: SignIn.Event): SignIn.State {
        return when (event) {
            is SignIn.Event.ValidEmail -> oldState.copy(validEmail = event.valid)
            is SignIn.Event.ValidPassword -> oldState.copy(validPassword = event.valid)
            is SignIn.Event.Loading -> oldState.copy(isLoading = true)
            is SignIn.Event.UpdateEmail -> {
                val valid = EmailValidator().isValid(event.email)
                setEvent { SignIn.Event.ValidEmail(valid) }
                oldState.copy(email = event.email)
            }

            is SignIn.Event.UpdatePassword -> {
                val valid = PasswordValidator().isValid(event.password)
                setEvent { SignIn.Event.ValidPassword(valid) }
                oldState.copy(password = event.password)
            }
            is SignIn.Event.Success -> oldState.copy(isLoading = false, isSuccess = true)
            is SignIn.Event.Error -> oldState.copy(isLoading = false, error = event.throwable)
            is SignIn.Event.Clear -> oldState.copy(error = null, email = "", password = "")
        }
    }

    override fun onIntent(intent: SignIn.Intent) {
        when (intent) {
            is SignIn.Intent.Back -> setEffect { SignIn.Effect.OnBack }
            is SignIn.Intent.Submit -> submit()
            is SignIn.Intent.ConfirmError -> setEvent { SignIn.Event.Clear }
        }
    }

    private fun submit() {
        viewModelScope.launch {
            try {
                /*if (!Validator.isValidEmail(state.value.email)) {
               /**
               Envia evento referente ao erro
               */
               setEvent {}
                }*/

                setEvent { SignIn.Event.Loading }
                when (val result = useCase.signIn(state.value.email, state.value.password)) {
                    is Result.Error -> setEvent { SignIn.Event.Error(result.error) }
                    is Result.Success -> setEvent { SignIn.Event.Success }
                }
            } catch (throwable: Throwable) {
                setEvent { SignIn.Event.Error(throwable) }
            }
        }
    }
}

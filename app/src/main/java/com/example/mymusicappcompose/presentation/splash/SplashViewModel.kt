package com.example.mymusicappcompose.presentation.splash

import androidx.lifecycle.viewModelScope
import com.example.mymusicappcompose.core.Result
import com.example.mymusicappcompose.domain.user.UserUseCase
import com.example.mymusicappcompose.presentation.architecture.MVIViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel : MVIViewModel<Splash.State, Splash.Event, Splash.Effect, Splash.Intent>() {

    private val useCase: UserUseCase = UserUseCase()

    override fun getInitial(): Splash.State = Splash.State.initial()

    override fun onReduce(oldState: Splash.State, event: Splash.Event): Splash.State {
        return when (event) {
            is Splash.Event.Error -> oldState.copy(error = event.throwable, isLoading = false)
            is Splash.Event.Loading -> oldState.copy(isLoading = event.value)
        }
    }

    override fun onIntent(intent: Splash.Intent) {
        when (intent) {
            is Splash.Intent.ConfirmError -> checkAccess()
        }
    }

    fun checkAccess() {
        viewModelScope.launch {

            setEvent { Splash.Event.Loading(true) }

            when (val result = useCase.isAuthorized()) {
                is Result.Error -> setEvent { Splash.Event.Error(result.error) }
                is Result.Success -> {

                    delay(500)

                    if (result.data) {
                        setEffect { Splash.Effect.OnShowMenu }
                    } else {
                        setEffect { Splash.Effect.OnShowWelcome }
                    }
                }
            }
        }
    }
}
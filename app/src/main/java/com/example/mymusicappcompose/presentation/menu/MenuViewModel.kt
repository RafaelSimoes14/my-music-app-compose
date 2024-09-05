package com.example.mymusicappcompose.presentation.menu

import androidx.lifecycle.viewModelScope
import com.example.mymusicappcompose.core.Result
import com.example.mymusicappcompose.domain.user.UserUseCase
import com.example.mymusicappcompose.presentation.architecture.MVIViewModel
import kotlinx.coroutines.launch

class MenuViewModel : MVIViewModel<Menu.State, Menu.Event, Menu.Effect, Menu.Intent>() {

    private val useCase: UserUseCase = UserUseCase()

    override fun getInitial(): Menu.State = Menu.State.initial()

    override fun onReduce(oldState: Menu.State, event: Menu.Event): Menu.State {
        return when (event) {
            is Menu.Event.SetError -> oldState.copy(isLoading = false, error = event.throwable)
            is Menu.Event.Loading -> oldState.copy(isLoading = true)
        }
    }

    override fun onIntent(intent: Menu.Intent) {
        when (intent) {
            is Menu.Intent.SignOut -> signOut()
            is Menu.Intent.OnGenres -> setEffect { Menu.Effect.OnGenres }
            is Menu.Intent.ConfirmError -> signOut()
            is Menu.Intent.OnSearch -> setEffect { Menu.Effect.OnSearch }
        }
    }

    private fun signOut() {
        viewModelScope.launch {

            setEvent { Menu.Event.Loading }

            when (val result = useCase.signOut()) {
                is Result.Error -> setEvent { Menu.Event.SetError(result.error) }
                is Result.Success -> setEffect { Menu.Effect.OnLogout }
            }
        }
    }
}
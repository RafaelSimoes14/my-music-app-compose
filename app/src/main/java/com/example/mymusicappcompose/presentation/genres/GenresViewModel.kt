package com.example.mymusicappcompose.presentation.genres

import androidx.lifecycle.viewModelScope
import com.example.mymusicappcompose.core.Result
import com.example.mymusicappcompose.domain.business.BusinessUseCase
import com.example.mymusicappcompose.presentation.architecture.MVIViewModel
import kotlinx.coroutines.launch

class GenresViewModel : MVIViewModel<GenresObject.State, GenresObject.Event, GenresObject.Effect, GenresObject.Intent>() {

    private val useCase: BusinessUseCase = BusinessUseCase()

    override fun getInitial(): GenresObject.State = GenresObject.State.initial()

    override fun onReduce(oldState: GenresObject.State, event: GenresObject.Event): GenresObject.State {
        return when (event) {
            is GenresObject.Event.SetError -> oldState.copy(
                isLoading = false,
                error = event.throwable
            )

            is GenresObject.Event.Loading -> oldState.copy(isLoading = true)
            is GenresObject.Event.LoadGenres -> oldState.copy(isLoading = false, genres = event.list )
        }
    }

    override fun onIntent(intent: GenresObject.Intent) {
        when (intent) {
            is GenresObject.Intent.OnRecommend -> setEffect { GenresObject.Effect.OnRecommend(intent.genre) }
            is GenresObject.Intent.Back -> setEffect { GenresObject.Effect.OnBack }
            is GenresObject.Intent.ConfirmError -> loadGenres()
        }
    }

    fun loadGenres() {
        viewModelScope.launch {
            setEvent { GenresObject.Event.Loading }
            when (val result = useCase.getGenres()) {
                is Result.Error -> {
                    setEvent { GenresObject.Event.SetError(result.error) }
                }

                is Result.Success -> {
                    if (result.data.isEmpty()) {
                        setEvent { GenresObject.Event.SetError(Throwable("List is empty!")) }
                    } else {
                        setEvent { GenresObject.Event.LoadGenres(result.data) }
                    }
                }
            }
        }
    }
}
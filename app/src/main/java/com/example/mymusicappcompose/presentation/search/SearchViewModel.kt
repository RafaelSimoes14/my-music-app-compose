package com.example.mymusicappcompose.presentation.search

import androidx.lifecycle.viewModelScope
import com.example.mymusicappcompose.core.Result
import com.example.mymusicappcompose.domain.business.BusinessUseCase
import com.example.mymusicappcompose.presentation.architecture.MVIViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class SearchViewModel :
    MVIViewModel<SearchObject.State, SearchObject.Event, SearchObject.Effect, SearchObject.Intent>() {

    private val useCase: BusinessUseCase = BusinessUseCase()
    override fun getInitial(): SearchObject.State = SearchObject.State.initial()

    private var job: Job? = null

    override fun onReduce(
        oldState: SearchObject.State,
        event: SearchObject.Event
    ): SearchObject.State {
        return when (event) {
            is SearchObject.Event.SetError -> oldState.copy(
                isLoading = false,
                error = event.throwable
            )

            is SearchObject.Event.Loading -> oldState.copy(isLoading = true)
            is SearchObject.Event.UpdateSearch -> {
                val newValue = event.searchField

                if (job != null) job?.cancel()

                job = viewModelScope.launch {
                    delay(600.milliseconds)
                    loadArtist()
                }

                oldState.copy(searchField = newValue)
            }

            is SearchObject.Event.LoadArtist -> oldState.copy(
                artists = event.list,
                isLoading = false
            )

            is SearchObject.Event.ClearSearch -> oldState.copy(searchField = "")
            is SearchObject.Event.ClearArtists -> oldState.copy(artists = emptyList())
            is SearchObject.Event.Clear -> oldState.copy(error = null)
        }
    }

    override fun onIntent(intent: SearchObject.Intent) {
        when (intent) {
            is SearchObject.Intent.OnArtist -> setEffect {
                SearchObject.Effect.OnArtist(intent.artist)
            }

            is SearchObject.Intent.Back -> setEffect { SearchObject.Effect.OnBack }
            is SearchObject.Intent.ConfirmError -> {
                loadArtist()
                setEvent { SearchObject.Event.Clear }
            }

            is SearchObject.Intent.OnClear -> {
                setEvent { SearchObject.Event.ClearSearch }
                setEvent { SearchObject.Event.ClearArtists }
            }
        }
    }

    private fun loadArtist() {
        viewModelScope.launch {
            val search = state.value.searchField
            if (search.isEmpty()) {
                setEvent { SearchObject.Event.ClearArtists }
                return@launch
            }

            setEvent { SearchObject.Event.Loading }
            when (val result = useCase.getArtists(search)) {
                is Result.Error -> {
                    setEvent { SearchObject.Event.SetError(result.error) }
                }

                is Result.Success -> {
                    if (result.data.isEmpty()) {
                        setEvent { SearchObject.Event.SetError(Throwable("Artist not found")) }
                    } else {
                        setEvent { SearchObject.Event.LoadArtist(result.data) }
                    }
                }
            }
        }
    }
}
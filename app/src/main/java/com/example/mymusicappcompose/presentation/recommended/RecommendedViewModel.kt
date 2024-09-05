package com.example.mymusicappcompose.presentation.recommended

import androidx.lifecycle.viewModelScope
import com.example.mymusicappcompose.core.Result
import com.example.mymusicappcompose.domain.business.BusinessUseCase
import com.example.mymusicappcompose.presentation.architecture.MVIViewModel
import kotlinx.coroutines.launch

class RecommendedViewModel :
    MVIViewModel<RecommendedObject.State, RecommendedObject.Event, RecommendedObject.Effect, RecommendedObject.Intent>() {

    private val useCase: BusinessUseCase = BusinessUseCase()

    override fun getInitial(): RecommendedObject.State = RecommendedObject.State.initial()

    override fun onReduce(
        oldState: RecommendedObject.State,
        event: RecommendedObject.Event
    ): RecommendedObject.State {
        return when (event) {
            is RecommendedObject.Event.SetError -> oldState.copy(
                isLoading = false,
                error = event.throwable
            )

            is RecommendedObject.Event.Loading -> oldState.copy(
                isLoading = true
            )

            is RecommendedObject.Event.LoadRecommended ->
                oldState.copy(isLoading = false, tracks = event.list)
        }
    }

    override fun onIntent(intent: RecommendedObject.Intent) {
        when (intent) {
            is RecommendedObject.Intent.Back -> setEffect { RecommendedObject.Effect.OnBack }
            is RecommendedObject.Intent.ConfirmError -> loadTracks(RecommendedActivity().genre)
            is RecommendedObject.Intent.OnTrackSpotify -> setEffect { RecommendedObject.Effect.OnTrackSpotify(intent.track) }
        }
    }

    fun loadTracks(genre: String) {
        viewModelScope.launch {
            setEvent { RecommendedObject.Event.Loading }
            when (val result = useCase.getTracksRecommended(genre)) {
                is Result.Error -> {
                    setEvent { RecommendedObject.Event.SetError(result.error) }
                }

                is Result.Success -> {
                    if (result.data.isEmpty()) {
                        setEvent { RecommendedObject.Event.SetError(Throwable("List is empty!")) }
                    } else {
                        setEvent { RecommendedObject.Event.LoadRecommended(result.data) }
                    }
                }
            }
        }
    }
}

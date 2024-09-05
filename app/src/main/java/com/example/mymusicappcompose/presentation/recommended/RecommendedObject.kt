package com.example.mymusicappcompose.presentation.recommended

import androidx.compose.runtime.Immutable
import com.example.mymusicappcompose.data.entity.spotify.recommendations.TrackRecommend
import com.example.mymusicappcompose.presentation.architecture.UiEffect
import com.example.mymusicappcompose.presentation.architecture.UiEvent
import com.example.mymusicappcompose.presentation.architecture.UiIntent
import com.example.mymusicappcompose.presentation.architecture.UiState

object RecommendedObject {

    @Immutable
    sealed interface Intent : UiIntent {
        data object Back : Intent
        data object ConfirmError : Intent
        data class OnTrackSpotify(val track: TrackRecommend?) : Intent
    }

    @Immutable
    data class State(
        val isLoading: Boolean,
        val error: Throwable?,
        val tracks: List<TrackRecommend>
    ) : UiState {
        companion object {
            fun initial() = State(
                isLoading = true,
                error = null,
                tracks = listOf()
            )
        }
    }

    @Immutable
    sealed interface Effect : UiEffect {
        data object OnBack : Effect
        data class OnTrackSpotify(val track: TrackRecommend?) : Effect
    }

    @Immutable
    sealed interface Event : UiEvent {
        data class SetError(val throwable: Throwable) : Event
        data class LoadRecommended(val list: List<TrackRecommend>) : Event

        data object Loading : Event

    }
}
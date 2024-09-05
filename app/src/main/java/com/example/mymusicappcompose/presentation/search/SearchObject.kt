package com.example.mymusicappcompose.presentation.search

import androidx.compose.runtime.Immutable
import com.example.mymusicappcompose.data.entity.spotify.artist.Artist
import com.example.mymusicappcompose.presentation.architecture.UiEffect
import com.example.mymusicappcompose.presentation.architecture.UiEvent
import com.example.mymusicappcompose.presentation.architecture.UiIntent
import com.example.mymusicappcompose.presentation.architecture.UiState

object SearchObject {

    @Immutable
    sealed interface Intent : UiIntent {
        data object Back : Intent
        data object ConfirmError : Intent
        data object OnClear : Intent

        data class OnArtist(val artist: Artist) : Intent
    }

    @Immutable
    data class State(
        val isLoading: Boolean,
        val error: Throwable?,
        val artists: List<Artist>,
        val searchField: String,
    ) : UiState {
        companion object {
            fun initial() = State(
                isLoading = false,
                error = null,
                artists = listOf(),
                searchField = ""
            )
        }
    }

    @Immutable
    sealed interface Effect : UiEffect {
        data class OnArtist(val artist: Artist) : Effect
        data object OnBack : Effect
    }

    @Immutable
    sealed interface Event : UiEvent {
        data class SetError(val throwable: Throwable) : Event
        data class LoadArtist(val list: List<Artist>) : Event
        data class UpdateSearch(val searchField: String) : Event

        data object Loading : Event
        data object ClearSearch : Event
        data object ClearArtists : Event
        data object Clear : Event
    }
}
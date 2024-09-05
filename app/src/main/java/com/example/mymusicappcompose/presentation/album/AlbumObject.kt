package com.example.mymusicappcompose.presentation.album

import androidx.compose.runtime.Immutable
import com.example.mymusicappcompose.data.entity.spotify.album.Album
import com.example.mymusicappcompose.data.entity.spotify.tracks.Track
import com.example.mymusicappcompose.presentation.architecture.UiEffect
import com.example.mymusicappcompose.presentation.architecture.UiEvent
import com.example.mymusicappcompose.presentation.architecture.UiIntent
import com.example.mymusicappcompose.presentation.architecture.UiState

object AlbumObject {
    @Immutable
    sealed interface Intent : UiIntent {
        data object Back : Intent
        data class ConfirmError(val idAlbum: String?) : Intent
        data class OnTrackSpotify(val track: Track) : Intent
        data class OnAlbumSpotify(val album: Album?) : Intent
    }

    @Immutable
    data class State(
        val isLoading: Boolean,
        val error: Throwable?,
        val album: Album?,
        val tracks: List<Track>
    ) : UiState {
        companion object {
            fun initial() = State(
                isLoading = true,
                error = null,
                album = null,
                tracks = listOf()
            )
        }
    }

    @Immutable
    sealed interface Effect : UiEffect {
        data object OnBack : Effect
        data class OnTrackSpotify(val track: Track) : Effect
        data class OnAlbumSpotify(val album: Album?) : Effect
    }

    @Immutable
    sealed interface Event : UiEvent {
        data class SetError(val throwable: Throwable) : Event
        data object Loading : Event
        data class UpdateAlbum(val value: Album) : Event
        data class LoadTracks(val tracks: List<Track>) : Event
        data object Clear : Event
    }
}
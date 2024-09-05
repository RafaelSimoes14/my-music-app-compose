package com.example.mymusicappcompose.presentation.artist

import androidx.compose.runtime.Immutable
import com.example.mymusicappcompose.data.entity.spotify.album.Album
import com.example.mymusicappcompose.data.entity.spotify.artist.Artist
import com.example.mymusicappcompose.data.entity.spotify.artist.Followers
import com.example.mymusicappcompose.data.entity.spotify.artist.ImageArtist
import com.example.mymusicappcompose.presentation.architecture.UiEffect
import com.example.mymusicappcompose.presentation.architecture.UiEvent
import com.example.mymusicappcompose.presentation.architecture.UiIntent
import com.example.mymusicappcompose.presentation.architecture.UiState

object ArtistObject {

    @Immutable
    sealed interface Intent : UiIntent {
        data object Back : Intent
        data class ConfirmError(val idArtist: String?) : Intent
        data class OnAlbum(val album: Album) : Intent
        data class OnArtistSpotify(val artist: Artist?) : Intent
    }

    @Immutable
    data class State(
        val isLoading: Boolean,
        val error: Throwable?,
        val artist: Artist?,
        val albums: List<Album>
    ) : UiState {

        fun getGenres(): String = artist?.genres?.toString()?.replace("[", "")?.replace("]","") ?: ""

        companion object {
            fun initial() = State(
                isLoading = true,
                error = null,
                artist = null,
                albums = listOf()
            )

            fun content() = State(
                isLoading = false,
                error = null,
                artist = Artist(
                    name = "Teste",
                    genres = listOf("a", "b"),
                    popularity = 1,
                    followers = Followers(href = null, total = 1),
                    images = listOf(
                        ImageArtist(1, "", 1)
                    )
                ),
                albums = listOf()
            )
        }
    }

    @Immutable
    sealed interface Effect : UiEffect {
        data object OnBack : Effect
        data class OnAlbum(val album: Album) : Effect
        data class OnArtistSpotify(val artist: Artist?) : Effect

    }

    @Immutable
    sealed interface Event : UiEvent {
        data class SetError(val throwable: Throwable) : Event
        data object Loading : Event
        data class UpdateArtist(val value: Artist) : Event
        data class LoadAlbums(val albums: List<Album>) : Event
        data object Clear : Event
    }
}
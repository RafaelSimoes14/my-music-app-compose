package com.example.mymusicappcompose.presentation.artist

import androidx.lifecycle.viewModelScope
import com.example.mymusicappcompose.core.Result
import com.example.mymusicappcompose.domain.business.BusinessUseCase
import com.example.mymusicappcompose.presentation.architecture.MVIViewModel
import kotlinx.coroutines.launch

class ArtistViewModel :
    MVIViewModel<ArtistObject.State, ArtistObject.Event, ArtistObject.Effect, ArtistObject.Intent>() {

    private val useCase: BusinessUseCase = BusinessUseCase()

    override fun getInitial(): ArtistObject.State = ArtistObject.State.initial()

    override fun onReduce(
        oldState: ArtistObject.State,
        event: ArtistObject.Event
    ): ArtistObject.State {
        return when (event) {
            is ArtistObject.Event.SetError -> oldState.copy(
                error = event.throwable,
                isLoading = false
            )

            is ArtistObject.Event.Loading -> oldState.copy(isLoading = true)
            is ArtistObject.Event.UpdateArtist -> oldState.copy(
                isLoading = false,
                artist = event.value
            )

            is ArtistObject.Event.LoadAlbums -> oldState.copy(
                albums = event.albums,
                isLoading = false
            )

            is ArtistObject.Event.Clear -> oldState.copy(error = null)
        }
    }

    override fun onIntent(intent: ArtistObject.Intent) {
        when (intent) {
            is ArtistObject.Intent.Back -> setEffect { ArtistObject.Effect.OnBack }
            is ArtistObject.Intent.ConfirmError -> {
                if (intent.idArtist != null) loadAlbums(intent.idArtist.toString())
                setEvent { ArtistObject.Event.Clear }
            }

            is ArtistObject.Intent.OnAlbum ->
                setEffect { ArtistObject.Effect.OnAlbum(intent.album) }

            is ArtistObject.Intent.OnArtistSpotify -> setEffect {
                ArtistObject.Effect.OnArtistSpotify(
                    intent.artist
                )
            }
        }
    }

    fun loadAlbums(idArtist: String) {
        viewModelScope.launch {

            setEvent { ArtistObject.Event.Loading }

            when (val result = useCase.getAlbums(idArtist)) {
                is Result.Error -> {
                    setEvent { ArtistObject.Event.SetError(result.error) }
                }

                is Result.Success -> {
                    if (result.data.isEmpty()) {
                        setEvent { ArtistObject.Event.SetError(Throwable("Albums not found")) }
                    } else {
                        setEvent { ArtistObject.Event.LoadAlbums(result.data) }
                    }
                }
            }
        }
    }
}

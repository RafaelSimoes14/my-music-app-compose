package com.example.mymusicappcompose.presentation.album

import androidx.lifecycle.viewModelScope
import com.example.mymusicappcompose.core.Result
import com.example.mymusicappcompose.domain.business.BusinessUseCase
import com.example.mymusicappcompose.presentation.architecture.MVIViewModel
import kotlinx.coroutines.launch

class AlbumViewModel :
    MVIViewModel<AlbumObject.State, AlbumObject.Event, AlbumObject.Effect, AlbumObject.Intent>() {

    private val useCase: BusinessUseCase = BusinessUseCase()

    override fun getInitial(): AlbumObject.State = AlbumObject.State.initial()

    override fun onReduce(
        oldState: AlbumObject.State,
        event: AlbumObject.Event
    ): AlbumObject.State {
        return when (event) {
            is AlbumObject.Event.SetError -> oldState.copy(
                error = event.throwable,
                isLoading = false
            )

            is AlbumObject.Event.Loading -> oldState.copy(isLoading = true)
            is AlbumObject.Event.UpdateAlbum -> oldState.copy(
                isLoading = false,
                album = event.value
            )

            is AlbumObject.Event.LoadTracks -> oldState.copy(
                tracks = event.tracks,
                isLoading = false
            )

            is AlbumObject.Event.Clear -> oldState.copy(error = null)
        }
    }

    override fun onIntent(intent: AlbumObject.Intent) {
        when (intent) {
            is AlbumObject.Intent.Back -> setEffect { AlbumObject.Effect.OnBack }
            is AlbumObject.Intent.ConfirmError -> {
                if (intent.idAlbum != null) loadTracks(intent.idAlbum.toString())
                setEvent { AlbumObject.Event.Clear }
            }

            is AlbumObject.Intent.OnTrackSpotify -> setEffect {
                AlbumObject.Effect.OnTrackSpotify(
                    intent.track
                )
            }

            is AlbumObject.Intent.OnAlbumSpotify -> setEffect {
                AlbumObject.Effect.OnAlbumSpotify(
                    intent.album
                )
            }
        }
    }

    fun loadTracks(idAlbums: String) {
        viewModelScope.launch {
            setEvent { AlbumObject.Event.Loading }
            when (val result = useCase.getTracks(idAlbums)) {
                is Result.Error -> {
                    setEvent { AlbumObject.Event.SetError(result.error) }
                }

                is Result.Success -> {
                    if (result.data.isEmpty()) {
                        setEvent { AlbumObject.Event.SetError(Throwable("Tracks not found!")) }
                    } else {
                        setEvent { AlbumObject.Event.LoadTracks(result.data) }
                    }
                }
            }
        }
    }
}
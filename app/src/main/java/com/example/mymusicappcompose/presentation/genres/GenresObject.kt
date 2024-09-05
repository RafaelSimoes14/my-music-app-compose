package com.example.mymusicappcompose.presentation.genres

import androidx.compose.runtime.Immutable
import com.example.mymusicappcompose.presentation.architecture.UiEffect
import com.example.mymusicappcompose.presentation.architecture.UiEvent
import com.example.mymusicappcompose.presentation.architecture.UiIntent
import com.example.mymusicappcompose.presentation.architecture.UiState

object GenresObject {

    @Immutable
    sealed interface Intent : UiIntent {
        data object Back : Intent
        data class OnRecommend(val genre: String) : Intent
        data object ConfirmError : Intent
    }

    @Immutable
    data class State(
        val isLoading: Boolean,
        val error: Throwable?,
        val genres: List<String>
    ) : UiState {
        companion object {
            fun initial() = State(
                isLoading = true,
                error = null,
                genres = listOf()
            )
        }
    }

    @Immutable
    sealed interface Effect : UiEffect {
        data class OnRecommend(val genre: String) : Effect
        data object OnBack : Effect
    }

    @Immutable
    sealed interface Event : UiEvent {
        data class SetError(val throwable: Throwable) : Event
        data class LoadGenres(val list: List<String>) : Event

        data object Loading : Event

    }
}
package com.example.mymusicappcompose.presentation.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mymusicappcompose.R
import com.example.mymusicappcompose.data.entity.spotify.artist.Artist
import com.example.mymusicappcompose.presentation.componets.GenericDialog
import com.example.mymusicappcompose.presentation.componets.LoadingDialog
import com.example.mymusicappcompose.presentation.componets.MySearch
import com.example.mymusicappcompose.ui.preview.DarkModePreview
import com.example.mymusicappcompose.ui.preview.LightModePreview
import com.example.mymusicappcompose.ui.theme.MyMusicAppComposeTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SearchScreen(
    artist: (Artist?) -> Unit,
    onBack: () -> Unit
) {
    val viewModel = SearchViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effects.collectLatest { effect ->
            when (effect) {
                is SearchObject.Effect.OnBack -> {
                    onBack()
                }

                is SearchObject.Effect.OnArtist -> {
                    artist(effect.artist)
                }
            }
        }
    }

    MyMusicAppComposeTheme {
        Surface {
            SearchContent(
                onState = { state },
                onIntent = { viewModel.setIntent { it } },
                onEvent = { viewModel.setEvent { it } }
            )
        }
    }
}

@Composable
fun SearchContent(
    onState: () -> SearchObject.State,
    onIntent: (SearchObject.Intent) -> Unit,
    onEvent: (SearchObject.Event) -> Unit
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        if (onState().isLoading) {
            LoadingDialog()
        }
        if (onState().error != null) {
            GenericDialog(title = stringResource(id = R.string.unable_to_search),
                question = onState().error?.message,
                onConfirm = { onIntent(SearchObject.Intent.ConfirmError) }
            )
        }

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            MySearch(
                onBack = { onIntent(SearchObject.Intent.Back) },
                value = onState().searchField,
                onClear = { onIntent(SearchObject.Intent.OnClear) },
                onValueChange = { onEvent(SearchObject.Event.UpdateSearch(it)) }
            )

            if (onState().artists.isEmpty() && !onState().isLoading) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_art_track_24),
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .weight(1f)
                            .align(Alignment.CenterHorizontally)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(onState().artists) { artist ->
                        val artistImage = artist.images

                        if (!artistImage.isNullOrEmpty()) {
                            ArtistItem(artist, onIntent = onIntent)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ArtistItem(
    artist: Artist,
    onIntent: (SearchObject.Intent) -> Unit
) {


    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { onIntent(SearchObject.Intent.OnArtist(artist)) }
    ) {
        AsyncImage(
            modifier = Modifier.size(80.dp),
            model = ImageRequest.Builder(LocalContext.current)
                .data(artist.images?.get(0)?.url)
                .crossfade(true)
                .build(),
            contentDescription = ""
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically),
            text = artist.name.toString(),
            style = TextStyle(
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )
        )
    }
}

@LightModePreview
@DarkModePreview
@Composable
fun SearchContentPreview() {
    MyMusicAppComposeTheme {
        SearchContent(onState = { SearchObject.State.initial() },
            onIntent = {}, onEvent = {}
        )
    }
}

@LightModePreview
@DarkModePreview
@Composable
fun ArtistItemPreview() {
    MyMusicAppComposeTheme {
        ArtistItem(artist = Artist(name = "tatu")) {
        }
    }
}
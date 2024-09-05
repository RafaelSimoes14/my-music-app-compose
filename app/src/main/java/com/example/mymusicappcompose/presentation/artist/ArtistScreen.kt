package com.example.mymusicappcompose.presentation.artist

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mymusicappcompose.R
import com.example.mymusicappcompose.data.entity.spotify.album.Album
import com.example.mymusicappcompose.data.entity.spotify.artist.Artist
import com.example.mymusicappcompose.presentation.componets.GenericDialog
import com.example.mymusicappcompose.presentation.componets.LoadingDialog
import com.example.mymusicappcompose.presentation.componets.MyTitle
import com.example.mymusicappcompose.ui.preview.DarkModePreview
import com.example.mymusicappcompose.ui.preview.LightModePreview
import com.example.mymusicappcompose.ui.theme.MyMusicAppComposeTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ArtistScreen(
    onBack: () -> Unit,
    artist: Artist,
    album: (Album?) -> Unit,
    onArtistSpotify: (Artist?) -> Unit
) {
    val viewModel = ArtistViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.setEvent { ArtistObject.Event.UpdateArtist(artist) }

        if (artist.id != null) viewModel.loadAlbums(artist.id)

        viewModel.effects.collectLatest { effect ->
            when (effect) {
                is ArtistObject.Effect.OnBack -> {
                    onBack()
                }

                is ArtistObject.Effect.OnAlbum -> {
                    album(effect.album)
                }

                is ArtistObject.Effect.OnArtistSpotify -> {
                    onArtistSpotify(effect.artist)
                }
            }
        }
    }

    MyMusicAppComposeTheme {
        Surface {
            ArtistContent(
                onState = { state },
                onIntent = { viewModel.setIntent { it } }
            )
        }
    }
}

@Composable
fun ArtistContent(
    onState: () -> ArtistObject.State,
    onIntent: (ArtistObject.Intent) -> Unit
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

        if (onState().isLoading) {
            LoadingDialog()
        }
        if (onState().error != null) {
            GenericDialog(title = stringResource(id = R.string.sign_up_title),
                question = onState().error?.message,
                onConfirm = { onIntent(ArtistObject.Intent.ConfirmError(idArtist = onState().artist?.id)) }
            )

        }
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MyTitle(
                onBack = { onIntent(ArtistObject.Intent.Back) },
                title = R.string.artist_title,
                btnBackDescription = R.string.button_back_description
            )

            val image: String? = try {
                onState().artist?.images?.get(0)?.url
            } catch (t: Throwable) {
                null
            }
            AsyncImage(
                modifier = Modifier.size(350.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image)
                    .placeholder(R.drawable.baseline_art_track_24)
                    .build(),
                contentDescription = ""
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                text = onState().artist?.name.toString(),
                style = TextStyle(textAlign = TextAlign.Center, fontSize = 16.sp),
                overflow = TextOverflow.Ellipsis
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                text = stringResource(id = R.string.genres, onState().getGenres()),
                style = TextStyle(textAlign = TextAlign.Center, fontSize = 16.sp),
                overflow = TextOverflow.Ellipsis
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(
                    id = R.string.popularity,
                    onState().artist?.popularity.toString()
                ),
                style = TextStyle(textAlign = TextAlign.Center, fontSize = 16.sp),
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(
                    id = R.string.followers,
                    onState().artist?.followers?.total.toString()
                ),
                style = TextStyle(textAlign = TextAlign.Center, fontSize = 16.sp),
            )
            Spacer(
                modifier = Modifier.size(8.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.spotify),
                contentDescription = "",
                modifier = Modifier
                    .size(50.dp)
                    .clickable { onIntent(ArtistObject.Intent.OnArtistSpotify(onState().artist)) })

            LazyRow {
                items(onState().albums) { album ->
                    val albumImage = album.images

                    if (!albumImage.isNullOrEmpty()) {
                        AlbumItem(album, onIntent = onIntent)
                    }
                }
            }
        }
    }
}

@Composable
fun AlbumItem(
    album: Album,
    onIntent: (ArtistObject.Intent) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onIntent(ArtistObject.Intent.OnAlbum(album)) }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(album.images?.get(0)?.url)
                .crossfade(true)
                .build(),
            modifier = Modifier.size(200.dp),
            contentDescription = ""
        )

        Text(
            text = album.name.toString(),
            modifier = Modifier.size(200.dp),
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
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
fun ArtistContentPreview() {
    MyMusicAppComposeTheme {
        ArtistContent(
            onState = { ArtistObject.State.content() },
            onIntent = {}
        )
    }
}

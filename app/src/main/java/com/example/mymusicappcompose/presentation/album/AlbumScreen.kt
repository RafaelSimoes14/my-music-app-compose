package com.example.mymusicappcompose.presentation.album

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mymusicappcompose.R
import com.example.mymusicappcompose.data.entity.spotify.album.Album
import com.example.mymusicappcompose.data.entity.spotify.tracks.Track
import com.example.mymusicappcompose.extensions.timeToString
import com.example.mymusicappcompose.presentation.componets.GenericDialog
import com.example.mymusicappcompose.presentation.componets.LoadingDialog
import com.example.mymusicappcompose.presentation.componets.MyTitle
import com.example.mymusicappcompose.ui.preview.DarkModePreview
import com.example.mymusicappcompose.ui.preview.LightModePreview
import com.example.mymusicappcompose.ui.theme.MyMusicAppComposeTheme
import kotlinx.coroutines.flow.collectLatest
import java.util.Date

@Composable
fun AlbumScreen(
    album: Album,
    onBack: () -> Unit,
    onTrackSpotify: (Track?) -> Unit,
    onAlbumSpotify: (Album?) -> Unit
) {
    val viewModel = AlbumViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.setEvent { AlbumObject.Event.UpdateAlbum(album) }

        if (album.id != null) viewModel.loadTracks(album.id)

        viewModel.effects.collectLatest { effect ->
            when (effect) {
                is AlbumObject.Effect.OnBack -> {
                    onBack()
                }

                is AlbumObject.Effect.OnTrackSpotify -> {
                    onTrackSpotify(effect.track)
                }

                is AlbumObject.Effect.OnAlbumSpotify -> {
                    onAlbumSpotify(effect.album)
                }
            }
        }
    }
    MyMusicAppComposeTheme {
        Surface {
            AlbumContent(
                onState = { state },
                onIntent = { viewModel.setIntent { it } }
            )
        }
    }
}

@Composable
fun AlbumContent(
    onState: () -> AlbumObject.State,
    onIntent: (AlbumObject.Intent) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        if (onState().isLoading) {
            LoadingDialog()
        }

        if (onState().error != null) {
            GenericDialog(title = stringResource(id = R.string.sign_up_title),
                question = onState().error?.message,
                onConfirm = { onIntent(AlbumObject.Intent.ConfirmError(idAlbum = onState().album?.id)) }
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
                onBack = { onIntent(AlbumObject.Intent.Back) },
                title = R.string.album_title,
                btnBackDescription = R.string.button_back_description
            )

            val image: String? = try {
                onState().album?.images?.get(0)?.url
            } catch (t: Throwable) {
                null
            }
            AsyncImage(
                modifier = Modifier.fillMaxHeight(.5f),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image)
                    .placeholder(R.drawable.baseline_art_track_24)
                    .build(),
                contentDescription = ""
            )
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = onState().album?.name.toString(),
                    maxLines = 1,
                    style = TextStyle(
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp
                    )
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(
                        id = R.string.release_date,
                        onState().album?.releaseDate.toString()
                    ),
                    style = TextStyle(
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp
                    )
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(
                        id = R.string.total_tracks,
                        onState().album?.totalTracks.toString()
                    ),
                    style = TextStyle(
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp
                    )
                )
                Spacer(
                    modifier = Modifier.size(8.dp)
                )
                Image(
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.CenterHorizontally)
                        .clickable { onIntent(AlbumObject.Intent.OnAlbumSpotify(onState().album)) },
                    painter = painterResource(
                        id = R.drawable.spotify
                    ),
                    contentDescription = ""
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxHeight()
            ) {
                items(onState().tracks) { track ->
                    TrackItem(track, onIntent = onIntent)
                }
            }
        }
    }
}

@Composable
fun TrackItem(track: Track, onIntent: (AlbumObject.Intent) -> Unit) {
    Row(
        modifier = Modifier
            .padding(32.dp)
            .fillMaxWidth()
            .clickable { onIntent(AlbumObject.Intent.OnTrackSpotify(track)) },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier,
            text = track.trackNumber.toString(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(
                textAlign = TextAlign.Start,
                fontSize = 12.sp
            )
        )

        Text(
            modifier = Modifier
                .fillMaxWidth(.5f),
            text = track.name.toString(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(
                textAlign = TextAlign.Start,
                fontSize = 12.sp
            )
        )

        Text(
            text = track.durationMS?.timeToString().toString(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(
                textAlign = TextAlign.Start,
                fontSize = 12.sp
            )
        )
    }
}

@LightModePreview
@DarkModePreview
@Composable
fun AlbumItemPreview() {
    MyMusicAppComposeTheme {
        Surface {
            TrackItem(track = Track(trackNumber = 1, name = "Teste", durationMS = Date().time)) {}
        }
    }
}

@LightModePreview
@DarkModePreview
@Composable
fun AlbumContentPreview() {
    MyMusicAppComposeTheme {
        Surface {
            AlbumContent(onState = { AlbumObject.State.initial() }) {}
        }
    }
}

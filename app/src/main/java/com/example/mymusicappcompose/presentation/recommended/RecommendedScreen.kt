package com.example.mymusicappcompose.presentation.recommended

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mymusicappcompose.R
import com.example.mymusicappcompose.data.entity.spotify.recommendations.TrackRecommend
import com.example.mymusicappcompose.presentation.componets.GenericDialog
import com.example.mymusicappcompose.presentation.componets.LoadingDialog
import com.example.mymusicappcompose.presentation.componets.MyTitle
import com.example.mymusicappcompose.ui.preview.DarkModePreview
import com.example.mymusicappcompose.ui.preview.LightModePreview
import com.example.mymusicappcompose.ui.theme.MyMusicAppComposeTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun RecommendedScreen(
    genre: String,
    onBack: () -> Unit,
    onTrackSpotify: (TrackRecommend?) -> Unit
) {
    val viewModel = RecommendedViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadTracks(genre)

        viewModel.effects.collectLatest { effect ->
            when (effect) {
                is RecommendedObject.Effect.OnBack -> {
                    onBack()
                }

                is RecommendedObject.Effect.OnTrackSpotify -> {
                    onTrackSpotify(effect.track)
                }
            }
        }
    }
    MyMusicAppComposeTheme {
        Surface {
            RecommendedContent(
                onState = { state },
                onIntent = { viewModel.setIntent { it } }
            )
        }
    }
}

@Composable
fun RecommendedContent(
    onState: () -> RecommendedObject.State,
    onIntent: (RecommendedObject.Intent) -> Unit
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        if (onState().isLoading) {
            LoadingDialog()
        }
        if (onState().error != null) {
            GenericDialog(title = stringResource(id = R.string.recommendations_title),
                question = onState().error?.message,
                onConfirm = { onIntent(RecommendedObject.Intent.ConfirmError) }
            )
        }

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            MyTitle(
                onBack = { onIntent(RecommendedObject.Intent.Back) },
                title = R.string.recommendations_title,
                btnBackDescription = R.string.button_back_description
            )

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp, 8.dp)
            ) {
                items(onState().tracks) { track ->

                    val images = track.album.images

                    if (!images.isNullOrEmpty()) {
                        TrackItem(track, onIntent = onIntent)
                    }
                }
            }
        }
    }
}

@Composable
fun TrackItem(
    track: TrackRecommend,
    onIntent: (RecommendedObject.Intent) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onIntent(RecommendedObject.Intent.OnTrackSpotify(track)) }
    ) {
        AsyncImage(
            modifier = Modifier.size(100.dp),
            model = ImageRequest.Builder(LocalContext.current)
                .data(track.album.images?.get(0)?.url)
                .crossfade(true)
                .build(),
            contentDescription = ""
        )
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                maxLines = 1,
                text = stringResource(id = R.string.music, track.name.toString()),
                style = TextStyle(textAlign = TextAlign.Center, fontSize = 16.sp),
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = stringResource(id = R.string.albumn) + track.album.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            if (track.artists?.get(0)?.name.toString().isNotEmpty()) {
                Text(
                    text = stringResource(
                        id = R.string.artist,
                        track.artists?.get(0)?.name.toString()
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@LightModePreview
@DarkModePreview
@Composable
fun RecommendedContentPreview() {
    MyMusicAppComposeTheme {
        RecommendedContent(
            onState = { RecommendedObject.State.initial() },
            onIntent = {}
        )
    }
}




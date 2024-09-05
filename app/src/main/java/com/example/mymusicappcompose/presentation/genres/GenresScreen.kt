package com.example.mymusicappcompose.presentation.genres

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mymusicappcompose.R
import com.example.mymusicappcompose.presentation.componets.GenericDialog
import com.example.mymusicappcompose.presentation.componets.LoadingDialog
import com.example.mymusicappcompose.presentation.componets.MyTitle
import com.example.mymusicappcompose.ui.preview.DarkModePreview
import com.example.mymusicappcompose.ui.preview.LightModePreview
import com.example.mymusicappcompose.ui.theme.MyMusicAppComposeTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun GenresScreen(
    onBack: () -> Unit,
    onRecommended: (String) -> Unit
) {

    val viewModel = GenresViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadGenres()
        viewModel.effects.collectLatest { effect ->
            when (effect) {
                is GenresObject.Effect.OnBack -> {
                    onBack()
                }

                is GenresObject.Effect.OnRecommend -> {
                    onRecommended(effect.genre)
                }
            }
        }
    }

    MyMusicAppComposeTheme {
        Surface {
            GenresContent(
                onState = { state },
                onIntent = { viewModel.setIntent { it } }
            )
        }
    }
}

@Composable
fun GenresContent(
    onState: () -> GenresObject.State,
    onIntent: (GenresObject.Intent) -> Unit
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        if (onState().isLoading) {
            LoadingDialog()
        }

        if (onState().error != null) {
            GenericDialog(title = stringResource(id = R.string.genres_title),
                question = onState().error?.message,
                onConfirm = { onIntent(GenresObject.Intent.ConfirmError) }
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
                onBack = { onIntent(GenresObject.Intent.Back) },
                title = R.string.genres_title,
                btnBackDescription = R.string.button_back_description
            )

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp, 8.dp)
            ) {
                items(onState().genres) { genre ->
                    GenreItem(genre = genre, onIntent = onIntent)
                }
            }
        }
    }
}

@Composable
fun GenreItem(
    genre: String,
    onIntent: (GenresObject.Intent) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onIntent(GenresObject.Intent.OnRecommend(genre))}
    ) {
        Text(
            text = genre,
            modifier = Modifier.fillMaxSize().padding(24.dp),
            style = TextStyle(textAlign = TextAlign.Center, fontSize = 16.sp)
        )
    }
}

@LightModePreview
@DarkModePreview
@Composable
fun GenresScreenPreview() {
    MyMusicAppComposeTheme {
        GenresContent(onState = { GenresObject.State.initial() }, onIntent = {})
    }
}

@LightModePreview
@DarkModePreview
@Composable
fun GenreItemPreview() {
    MyMusicAppComposeTheme {
        GenreItem(genre = "Rock") {}
    }
}

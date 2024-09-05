package com.example.mymusicappcompose.presentation.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mymusicappcompose.R
import com.example.mymusicappcompose.presentation.componets.GenericDialog
import com.example.mymusicappcompose.presentation.componets.LoadingDialog
import com.example.mymusicappcompose.ui.preview.DarkModePreview
import com.example.mymusicappcompose.ui.preview.LightModePreview
import com.example.mymusicappcompose.ui.theme.MyMusicAppComposeTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MenuScreen(
    onGenres: () -> Unit,
    onWelcome: () -> Unit,
    onSearch: () -> Unit
) {
    val viewModel = MenuViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effects.collectLatest { effect ->
            when (effect) {
                is Menu.Effect.OnGenres -> {
                    onGenres()
                }

                is Menu.Effect.OnLogout -> {
                    onWelcome()
                }

                is Menu.Effect.OnSearch -> {
                    onSearch()
                }
            }
        }
    }

    MyMusicAppComposeTheme {
        Surface {
            MenuContent(
                onState = { state },
                onIntent = { viewModel.setIntent { it } }
            )
        }
    }
}


@Composable
fun MenuContent(
    onState: () -> Menu.State,
    onIntent: (Menu.Intent) -> Unit
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        if (onState().isLoading) {
            LoadingDialog()
        }

        if (onState().error != null) {
            GenericDialog(
                title = stringResource(id = R.string.communication_failure),
                question = onState().error?.message,
                onConfirm = { onIntent(Menu.Intent.ConfirmError) }
            )
        }
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(0.5f),
                onClick = { onIntent(Menu.Intent.OnSearch) }
            ) {
                Text(text = stringResource(R.string.search_title), fontSize = 16.sp)
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            }

            Button(
                modifier = Modifier.fillMaxWidth(0.5f),
                onClick = { onIntent(Menu.Intent.OnGenres) }
            ) {
                Text(text = stringResource(R.string.genres_title), fontSize = 16.sp)
                Icon(imageVector = Icons.Default.Audiotrack, contentDescription = null)
            }

            Button(
                modifier = Modifier.fillMaxWidth(0.5f),
                onClick = { onIntent(Menu.Intent.SignOut) }
            ) {
                Text(text = stringResource(R.string.sign_out), fontSize = 16.sp)
                Icon(imageVector = Icons.Default.Close, contentDescription = null)
            }
        }

    }
}

@LightModePreview
@DarkModePreview
@Composable
fun MenuScreenPreview() {
    MyMusicAppComposeTheme {
        MenuContent(onState = { Menu.State.initial() }, {})
    }
}
package com.example.mymusicappcompose.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mymusicappcompose.R
import com.example.mymusicappcompose.presentation.componets.GenericDialog
import com.example.mymusicappcompose.presentation.componets.LoadingDialog
import com.example.mymusicappcompose.ui.preview.DarkModePreview
import com.example.mymusicappcompose.ui.preview.LightModePreview
import com.example.mymusicappcompose.ui.theme.MyMusicAppComposeTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SplashScreen(
    onShowMenu: () -> Unit,
    onShowWelcome: () -> Unit
) {
    val viewModel = SplashViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.checkAccess()
        viewModel.effects.collectLatest { effect ->
            when (effect) {
                is Splash.Effect.OnShowMenu -> {
                    onShowMenu()
                }

                is Splash.Effect.OnShowWelcome -> {
                    onShowWelcome()
                }
            }
        }
    }

    MyMusicAppComposeTheme {
        Surface {
            SplashContent(
                onState = { state },
                onIntent = { viewModel.setIntent { it } },
            )
        }
    }
}

@Composable
fun SplashContent(
    onState: () -> Splash.State,
    onIntent: (Splash.Intent) -> Unit

) {

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        if (onState().isLoading) {
            LoadingDialog()
        }
        if (onState().error != null) {
            GenericDialog(title = stringResource(id = R.string.sign_up_title),
                question = onState().error?.message,
                onConfirm = { onIntent(Splash.Intent.ConfirmError) }
            )
        }
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(R.drawable.cat_guitar),
                contentDescription = "",
                modifier = Modifier
                    .padding(8.dp)
                    .size(350.dp)
            )
        }
    }
}

@LightModePreview
@DarkModePreview
@Composable
fun SplashScreenPreview() {
    MyMusicAppComposeTheme {
        Surface {
            SplashContent(
                onState = { Splash.State.initial() },
                onIntent = {}
            )
        }
    }
}





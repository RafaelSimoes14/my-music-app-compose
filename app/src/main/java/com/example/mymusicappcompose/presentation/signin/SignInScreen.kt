package com.example.mymusicappcompose.presentation.signin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mymusicappcompose.R
import com.example.mymusicappcompose.presentation.componets.GenericDialog
import com.example.mymusicappcompose.presentation.componets.LoadingDialog
import com.example.mymusicappcompose.presentation.componets.MyButton
import com.example.mymusicappcompose.presentation.componets.MyInput
import com.example.mymusicappcompose.presentation.componets.MyPasswordInput
import com.example.mymusicappcompose.presentation.componets.MyTitle
import com.example.mymusicappcompose.ui.preview.DarkModePreview
import com.example.mymusicappcompose.ui.preview.LightModePreview
import com.example.mymusicappcompose.ui.theme.MyMusicAppComposeTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignInScreen(
    onBack: () -> Unit,
    onSuccess: () -> Unit
) {
    val viewModel = SignInViewModel()
    val state by viewModel.state.collectAsState()

    /**
     * Inicia assim que a Composable View que nesse caso e a SignInScreen for exibida
     */
    LaunchedEffect(Unit) {
        viewModel.effects.collectLatest { effect ->
            when (effect) {
                is SignIn.Effect.OnBack -> { onBack() }
                is SignIn.Effect.OnError -> {}
                is SignIn.Effect.OnSuccess -> { onSuccess() }
            }
        }
    }

    MyMusicAppComposeTheme {
        Surface {
            SignInContent(
                onState = { state },
                onIntent = { viewModel.setIntent { it } },
                onEvent = { viewModel.setEvent { it } }
            )
        }
    }
}

@Composable
fun SignInContent(
    onState: () -> SignIn.State,
    onIntent: (SignIn.Intent) -> Unit,
    onEvent: (SignIn.Event) -> Unit
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        if (onState().isLoading) {
            LoadingDialog()
        }

        if (onState().error != null) {
            GenericDialog(
                title = stringResource(id = R.string.sign_up_title),
                question = onState().error?.message,
                onConfirm = { onIntent(SignIn.Intent.ConfirmError) }
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
                onBack = { onIntent(SignIn.Intent.Back) },
                title = R.string.sign_in_title,
                btnBackDescription = R.string.button_back_description
            )

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                MyInput(
                    modifier = Modifier.fillMaxWidth(),
                    value = onState().email,
                    onValueChange = { onEvent(SignIn.Event.UpdateEmail(it)) },
                    trailingIcon = Icons.Default.Email,
                    label = R.string.email,
                    trailingIconDescription = R.string.email_icon_description
                )

                MyPasswordInput(
                    modifier = Modifier.fillMaxWidth(),
                    value = onState().password,
                    onValueChange = { onEvent(SignIn.Event.UpdatePassword(it)) },
                    label = R.string.password,
                    trailingIconDescription = R.string.password_icon_description
                )
            }
            MyButton(
                onConfirm = { onIntent(SignIn.Intent.Submit) },
                text = stringResource(id = R.string.sign_in_button),
                enabled = onState().isButtonEnabled()

            )

        }
    }
}

@LightModePreview
@DarkModePreview
@Composable
fun SignInScreenPreview() {
    MyMusicAppComposeTheme {
        Surface {
            SignInContent(onState = { SignIn.State.initial() }, {}, {})
        }
    }
}
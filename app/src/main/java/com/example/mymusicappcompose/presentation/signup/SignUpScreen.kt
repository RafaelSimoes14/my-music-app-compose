package com.example.mymusicappcompose.presentation.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
fun SignUpScreen(
    onBack: () -> Unit,
    onSuccess: () -> Unit,
    onTerms: () -> Unit
) {
    val viewModel = SignUpViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effects.collectLatest { effect ->
            when (effect) {
                is SignUp.Effect.OnBack -> onBack()
                is SignUp.Effect.OnError -> {}
                is SignUp.Effect.OnSuccess -> {
                    onSuccess()
                }

                is SignUp.Effect.OnTerms -> onTerms()
            }
        }
    }

    MyMusicAppComposeTheme {
        Surface {
            SignUpContent(
                onState = { state },
                onIntent = { viewModel.setIntent { it } },
                onEvent = { viewModel.setEvent { it } }
            )
        }
    }
}

@Composable
fun SignUpContent(
    onState: () -> SignUp.State,
    onIntent: (SignUp.Intent) -> Unit,
    onEvent: (SignUp.Event) -> Unit
) {

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        if (onState().isLoading) {
            LoadingDialog()
        }

        if (onState().error != null) {
            GenericDialog(
                title = stringResource(id = R.string.unable_to_register),
                question = onState().error?.message,
                onConfirm = { onIntent(SignUp.Intent.ConfirmError) }
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
                onBack = { onIntent(SignUp.Intent.Back) },
                title = R.string.sign_up_title,
                btnBackDescription = R.string.button_back_description
            )

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                MyInput(
                    modifier = Modifier.fillMaxWidth(),
                    value = onState().email,
                    onValueChange = { onEvent(SignUp.Event.UpdateEmail(it)) },
                    trailingIcon = Icons.Default.Email,
                    label = R.string.email,
                    trailingIconDescription = R.string.email_icon_description
                )
                MyPasswordInput(
                    modifier = Modifier.fillMaxWidth(),
                    value = onState().password,
                    onValueChange = { onEvent(SignUp.Event.UpdatePassword(it)) },
                    label = R.string.password,
                    trailingIconDescription = R.string.password_icon_description
                )
                MyPasswordInput(
                    modifier = Modifier.fillMaxWidth(),
                    value = onState().confirmationPassword,
                    onValueChange = { onEvent(SignUp.Event.UpdateConfirmPassword(it)) },
                    label = R.string.confirm_password,
                    trailingIconDescription = R.string.confirm_password_icon_description
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = onState().isChecked,
                        onCheckedChange = { onEvent(SignUp.Event.UserCheck(!onState().isChecked)) },
                        modifier = Modifier.padding(8.dp)
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(1f),
                        text = stringResource(id = R.string.check_box),
                        style = TextStyle(fontSize = 12.sp)
                    )
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(100.dp, 0.dp),
                    onClick = { onIntent(SignUp.Intent.Terms) }) {
                    Text(text = stringResource(id = R.string.read_terms))
                }
            }
            MyButton(
                onConfirm = { onIntent(SignUp.Intent.Register) },
                text = stringResource(id = R.string.sign_up_button),
                enabled = onState().isButtonEnabled()
            )
        }


    }
}

@LightModePreview
@DarkModePreview
@Composable
fun SignUpScreenPreview() {
    MyMusicAppComposeTheme {
        SignUpContent(onState = { SignUp.State.initial() }, {}, {})
    }
}

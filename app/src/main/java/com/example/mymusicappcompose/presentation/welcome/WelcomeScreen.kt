package com.example.mymusicappcompose.presentation.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mymusicappcompose.R
import com.example.mymusicappcompose.ui.preview.DarkModePreview
import com.example.mymusicappcompose.ui.preview.LightModePreview
import com.example.mymusicappcompose.ui.theme.MyMusicAppComposeTheme

@Composable
fun WelcomeScreen(
    onSignUp: () -> Unit,
    onSignIn: () -> Unit
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Text(
                text = stringResource(id = R.string.welcome_title),
                style = TextStyle(fontSize = 24.sp)
            )
            Image(
                painter = painterResource(id = R.drawable.cat_guitar),
                contentDescription = null,
                modifier = Modifier
                    .padding(8.dp)
                    .size(350.dp)
            )
            Button(
                onClick = { onSignIn.invoke() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.sign_in_button))
            }
            Button(
                onClick = { onSignUp.invoke() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.sign_up_button))
            }
        }
    }
}

@LightModePreview
@DarkModePreview
@Composable
fun WelcomePreview() {
    MyMusicAppComposeTheme {
        WelcomeScreen(
            onSignUp = {},
            onSignIn = {}
        )
    }
}
package com.example.mymusicappcompose.presentation.welcome

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mymusicappcompose.R
import com.example.mymusicappcompose.presentation.signin.SignInActivity
import com.example.mymusicappcompose.presentation.signup.SignUpActivity
import com.example.mymusicappcompose.ui.theme.MyMusicAppComposeTheme

class WelcomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyMusicAppComposeTheme {
                WelcomeScreen(
                    onSignUp = { showSignUp() },
                    onSignIn = { showSignIn() }
                )
            }
        }
    }

    private fun showSignUp() {
        val intent = Intent(baseContext, SignUpActivity()::class.java)
        startActivity(intent)
    }

    private fun showSignIn() {
        val intent = Intent(baseContext, SignInActivity()::class.java)
        startActivity(intent)
    }
}

@Composable
fun WelcomeScreen(
    onSignUp: () -> Unit,
    onSignIn: () -> Unit
) {

    Surface {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp)
            ) {
                Text(text = stringResource(id = R.string.sign_in_button))
            }
            Button(
                onClick = { onSignUp.invoke() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp)
            ) {
                Text(text = stringResource(id = R.string.sign_up_button))
            }

        }
    }

}


@Composable
@Preview
fun WelcomePreview() {
    MyMusicAppComposeTheme {
        WelcomeScreen(
            onSignUp = {},
            onSignIn = {}
        )
    }
}
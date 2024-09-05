package com.example.mymusicappcompose.presentation.welcome

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import com.example.mymusicappcompose.presentation.signin.SignInActivity
import com.example.mymusicappcompose.presentation.signup.SignUpActivity
import com.example.mymusicappcompose.ui.theme.MyMusicAppComposeTheme

class WelcomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyMusicAppComposeTheme {
                Surface {
                    WelcomeScreen(
                        onSignUp = { showSignUp() },
                        onSignIn = { showSignIn() }
                    )
                }
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

package com.example.mymusicappcompose.presentation.signin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.mymusicappcompose.ui.theme.MyMusicAppComposeTheme

class SignInActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyMusicAppComposeTheme {
                SignInScreen(
                    onBack = { onBackPressedDispatcher.onBackPressed() },
                    onSuccess = {

                    }
                )
            }
        }
    }
}
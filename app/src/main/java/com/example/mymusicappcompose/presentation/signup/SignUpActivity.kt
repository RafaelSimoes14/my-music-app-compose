package com.example.mymusicappcompose.presentation.signup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mymusicappcompose.ui.theme.MyMusicAppComposeTheme

class SignUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyMusicAppComposeTheme {
                SignUpScreen(
                    onBack = {},
                    onSuccess = {},
                    onTerms = {}
                )
            }

        }
    }
}
package com.example.mymusicappcompose.presentation.signup

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mymusicappcompose.presentation.menu.MenuActivity
import com.example.mymusicappcompose.ui.theme.MyMusicAppComposeTheme

class SignUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SignUpScreen(
                onBack = { onBackPressedDispatcher.onBackPressed() },
                onSuccess = { showMenu() },
                onTerms = {}
            )
        }
    }

    private fun showMenu() {
        val intent = Intent(baseContext, MenuActivity::class.java)
        startActivity(intent)
        finish()
    }
}
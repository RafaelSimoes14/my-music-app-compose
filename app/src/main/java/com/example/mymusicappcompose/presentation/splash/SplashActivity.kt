package com.example.mymusicappcompose.presentation.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mymusicappcompose.presentation.menu.MenuActivity
import com.example.mymusicappcompose.presentation.welcome.WelcomeActivity

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SplashScreen(
                onShowMenu = { showMenu() },
                onShowWelcome = { showWelcome() }
            )
        }
    }

    private fun showMenu() {
        val intent = Intent(baseContext, MenuActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showWelcome() {
        val intent = Intent(baseContext, WelcomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}

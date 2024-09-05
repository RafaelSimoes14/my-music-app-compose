package com.example.mymusicappcompose.presentation.menu

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mymusicappcompose.presentation.genres.GenresActivity
import com.example.mymusicappcompose.presentation.search.SearchActivity
import com.example.mymusicappcompose.presentation.welcome.WelcomeActivity
import com.example.mymusicappcompose.ui.theme.MyMusicAppComposeTheme

class MenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MenuScreen(
                onGenres = { showGenres() },
                onWelcome = { showWelcome() },
                onSearch = { showSearch() }
            )
        }
    }

    private fun showSearch() {
        val intent = Intent(baseContext, SearchActivity::class.java)
        startActivity(intent)
    }

    private fun showWelcome() {
        val intent = Intent(baseContext, WelcomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showGenres() {
        val intent = Intent(baseContext, GenresActivity::class.java)
        startActivity(intent)
    }
}
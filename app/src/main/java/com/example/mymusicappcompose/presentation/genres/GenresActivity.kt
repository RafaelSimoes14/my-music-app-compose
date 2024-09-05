package com.example.mymusicappcompose.presentation.genres

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mymusicappcompose.log.logDebug
import com.example.mymusicappcompose.presentation.recommended.RecommendedActivity
import com.example.mymusicappcompose.ui.theme.MyMusicAppComposeTheme

class GenresActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GenresScreen(
                onBack = { onBackPressedDispatcher.onBackPressed() },
                onRecommended = { showRecommended(it) }
            )
        }
    }

    private fun showRecommended(genre: String) {
        logDebug("GENRE $genre")
        val intent = Intent(baseContext, RecommendedActivity::class.java)
        intent.putExtra("genre", genre)
        startActivity(intent)
    }
}
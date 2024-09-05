package com.example.mymusicappcompose.presentation.search

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mymusicappcompose.data.entity.spotify.artist.Artist
import com.example.mymusicappcompose.log.logDebug
import com.example.mymusicappcompose.presentation.artist.ArtistActivity

class SearchActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SearchScreen(
                onBack = { onBackPressedDispatcher.onBackPressed() },
                artist = { showArtist(it) }
            )
        }
    }

    private fun showArtist(artist: Artist?) {
        logDebug("Artist: $artist")
        val intent = Intent(baseContext, ArtistActivity::class.java)
        intent.putExtra("artist", artist)
        startActivity(intent)
    }
}
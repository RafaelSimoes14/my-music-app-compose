package com.example.mymusicappcompose.presentation.recommended

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mymusicappcompose.data.entity.spotify.recommendations.TrackRecommend
import com.example.mymusicappcompose.data.entity.spotify.tracks.Track
import com.example.mymusicappcompose.extensions.openSpotify
import com.example.mymusicappcompose.ui.theme.MyMusicAppComposeTheme

class RecommendedActivity : ComponentActivity() {

    lateinit var genre: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        genre = intent.extras?.getString("genre") ?: "acoustic"

        setContent {
            RecommendedScreen(
                genre = genre,
                onBack = { onBackPressedDispatcher.onBackPressed() },
                onTrackSpotify = { showSpotifyTrack(it) }
            )
        }
    }

    private fun showSpotifyTrack(track: TrackRecommend?) {
        openSpotify(track?.externalUrls?.spotify)
    }
}
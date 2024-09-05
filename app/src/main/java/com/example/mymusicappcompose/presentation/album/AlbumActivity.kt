package com.example.mymusicappcompose.presentation.album

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import com.example.mymusicappcompose.data.entity.spotify.album.Album
import com.example.mymusicappcompose.data.entity.spotify.tracks.Track
import com.example.mymusicappcompose.extensions.openSpotify

class AlbumActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val album: Album =
            intent.extras?.getParcelable("album") ?: throw IllegalArgumentException()

        setContent {
            Surface {
                AlbumScreen(
                    onBack = { onBackPressedDispatcher.onBackPressed() },
                    album = album,
                    onTrackSpotify = { showTrackSpotify(it) },
                    onAlbumSpotify = { showAlbumSpotify(it) }
                )
            }
        }
    }

    private fun showAlbumSpotify(album: Album?) {
        openSpotify(album?.externalUrls?.spotify)

    }

    private fun showTrackSpotify(track: Track?) {
        openSpotify(track?.externalUrls?.spotify)
    }
}
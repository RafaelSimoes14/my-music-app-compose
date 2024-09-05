package com.example.mymusicappcompose.presentation.artist

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mymusicappcompose.data.entity.spotify.album.Album
import com.example.mymusicappcompose.data.entity.spotify.artist.Artist
import com.example.mymusicappcompose.extensions.openSpotify
import com.example.mymusicappcompose.log.logDebug
import com.example.mymusicappcompose.presentation.album.AlbumActivity

class ArtistActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val artist: Artist =
            intent.extras?.getParcelable("artist") ?: throw IllegalArgumentException()
        logDebug("artist: $artist")

        setContent {
            ArtistScreen(
                onBack = { onBackPressedDispatcher.onBackPressed() },
                artist = artist,
                album = { showAlbum(it) },
                onArtistSpotify = { showArtistSpotify(it) }
            )
        }
    }

    private fun showArtistSpotify(artist: Artist?) {
        openSpotify(artist?.externalUrls?.spotify)
    }

    private fun showAlbum(album: Album?) {
        val intent = Intent(baseContext, AlbumActivity::class.java)
        intent.putExtra("album", album)
        startActivity(intent)
    }
}
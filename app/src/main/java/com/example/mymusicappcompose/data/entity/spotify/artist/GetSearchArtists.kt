package com.example.mymusicappcompose.data.entity.spotify.artist

import android.os.Parcelable
import com.example.mymusicappcompose.data.entity.spotify.artist.Artists
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetSearchArtists(val artists: Artists): Parcelable

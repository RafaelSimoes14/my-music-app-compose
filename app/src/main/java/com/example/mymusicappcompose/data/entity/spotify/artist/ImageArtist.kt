package com.example.mymusicappcompose.data.entity.spotify.artist

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageArtist(
    val height: Long,
    val url: String,
    val width: Long
) : Parcelable

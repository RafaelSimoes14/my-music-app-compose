package com.example.mymusicappcompose.data.entity.spotify.album

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageAlbum(
    val url: String,
    val height: Long,
    val width: Long
) : Parcelable

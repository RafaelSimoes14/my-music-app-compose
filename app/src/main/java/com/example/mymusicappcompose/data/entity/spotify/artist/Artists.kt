package com.example.mymusicappcompose.data.entity.spotify.artist

import android.os.Parcelable
import com.example.mymusicappcompose.data.entity.spotify.artist.Artist
import kotlinx.parcelize.Parcelize

@Parcelize
data class Artists(
    val href: String,
    val items: List<Artist>,
    val limit: Long,
    val next: String,
    val offset: Long,
    //val previous: Any? = null,
    val total: Long
): Parcelable

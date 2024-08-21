package com.example.mymusicappcompose.data.entity.spotify.album

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetArtistAlbums(
    val items: List<Album>,
    val href: String? = null,
    val limit: Long,
    val next: String,
    val offset: Long,
    //val previous: Any? = null,
    val total: Long? = null
) : Parcelable

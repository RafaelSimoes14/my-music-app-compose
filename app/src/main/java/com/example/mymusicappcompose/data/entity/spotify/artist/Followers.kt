package com.example.mymusicappcompose.data.entity.spotify.artist

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Followers(
    val href: String? = null,
    val total: Long? = null
) : Parcelable

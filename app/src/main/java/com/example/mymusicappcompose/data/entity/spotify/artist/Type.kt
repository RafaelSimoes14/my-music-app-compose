package com.example.mymusicappcompose.data.entity.spotify.artist

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Type(val value: String? = null) : Parcelable {
    Artist("artist");

    companion object {
        fun fromValue(value: String): Type = when (value) {
            "artist" -> Artist
            else -> throw IllegalArgumentException()
        }
    }
}

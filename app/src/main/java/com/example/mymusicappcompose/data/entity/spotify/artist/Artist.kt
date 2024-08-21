package com.example.mymusicappcompose.data.entity.spotify.artist

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Artist(
    @SerializedName("external_urls")
    val externalUrls: ExternalUrlsArtist? = null,
    val followers: Followers? = null,
    val genres: List<String>? = null,
    val href: String? = null,
    val id: String? = null,
    val images: List<ImageArtist>? = null,
    val name: String? = null,
    val popularity: Long? = null,
    val type: Type? = null,
    val uri: String? = null
) : Parcelable



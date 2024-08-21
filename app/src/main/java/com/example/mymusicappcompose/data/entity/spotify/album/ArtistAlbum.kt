package com.example.mymusicappcompose.data.entity.spotify.album

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArtistAlbum(
    @SerializedName("external_urls")
    val externalUrls: ExternalUrlsAlbums? = null,
    val href: String? = null,
    val id: ID? = null,
    val name: Name? = null,
    val type: Type? = null,
    val uri: URI? = null
) : Parcelable

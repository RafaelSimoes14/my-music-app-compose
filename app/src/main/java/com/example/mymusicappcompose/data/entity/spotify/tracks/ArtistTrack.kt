package com.example.mymusicappcompose.data.entity.spotify.tracks

import com.google.gson.annotations.SerializedName

data class ArtistTrack(
    @SerializedName("external_urls")
    val externalUrls: ExternalUrlsArtistTrack? = null,
    val href: String? = null,
    val id: String? = null,
    val name: String? = null,
    val type: String? = null,
    val uri: String? = null
)

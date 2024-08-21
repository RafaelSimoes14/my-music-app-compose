package com.example.mymusicappcompose.data.entity.spotify.tracks

import com.example.mymusicappcompose.data.entity.spotify.tracks.ArtistTrack
import com.example.mymusicappcompose.data.entity.spotify.tracks.ExternalUrlsArtistTrack
import com.google.gson.annotations.SerializedName

data class Track(
    val artists: List<ArtistTrack>? = null,

    @SerializedName("disc_number")
    val discNumber: Long? = null,

    @SerializedName("duration_ms")
    val durationMS: Long? = null,

    val explicit: Boolean? = null,

    @SerializedName("external_urls")
    val externalUrls: ExternalUrlsArtistTrack? = null,

    val href: String? = null,
    val id: String? = null,

    @SerializedName("is_local")
    val isLocal: Boolean? = null,

    @SerializedName("is_playable")
    val isPlayable: Boolean? = null,

    val name: String? = null,

    @SerializedName("preview_url")
    val previewURL: String? = null,

    @SerializedName("track_number")
    val trackNumber: Long? = null,

    val type: String? = null,
    val uri: String? = null
)

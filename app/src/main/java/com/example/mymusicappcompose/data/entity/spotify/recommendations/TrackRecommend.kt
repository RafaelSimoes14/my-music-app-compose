package com.example.mymusicappcompose.data.entity.spotify.recommendations

import com.google.gson.annotations.SerializedName

data class TrackRecommend(
    val album: AlbumRecommend,
    val artists: List<ArtistsRecommend>?,
    @SerializedName("disc_number") val discNumber: Int? = null,
    @SerializedName("duration_ms") val durationMs: Int? = null,
    val explicit: Boolean,
    @SerializedName("external_ids") val externalIds: ExternalIds,
    @SerializedName("external_urls") val externalUrls: ExternalUrlsRecommend,
    val href: String? = null,
    val id: String? = null,
    @SerializedName("is_local") val isLocal: Boolean? = null,
    @SerializedName("is_playable") val isPlayable: Boolean? = null,
    val name: String? = null,
    val popularity: Int? = null,
    @SerializedName("preview_url") val previewUrl: String? = null,
    @SerializedName("track_number") val trackNumber: Int? = null,
    val type: String? = null,
    val uri: String? = null
)

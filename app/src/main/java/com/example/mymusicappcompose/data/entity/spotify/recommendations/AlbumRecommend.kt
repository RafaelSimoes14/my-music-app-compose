package com.example.mymusicappcompose.data.entity.spotify.recommendations

import com.google.gson.annotations.SerializedName

data class AlbumRecommend(
    @SerializedName("album_type") val albumType : String,
    val artists : List<ArtistsRecommend>?,
    @SerializedName("external_urls") val externalUrls : ExternalUrlsRecommend,
    val href : String,
    val id : String,
    val images : List<ImageRecommend>?,
    @SerializedName("is_playable") val isPlayable : Boolean,
    val name : String,
    @SerializedName("release_date") val releaseDate : String,
    @SerializedName("release_date_precision") val releaseDatePrecision : String,
    @SerializedName("total_tracks") val totalTracks : Int,
    val type : String,
    val uri : String
)

package com.example.mymusicappcompose.data.entity.spotify.album

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Album(
    @SerializedName( "album_type")
    val albumType: AlbumGroup? = null,

    @SerializedName( "total_tracks")
    val totalTracks: Long? = null,

    @SerializedName( "is_playable")
    val isPlayable: Boolean? = null,

    @SerializedName( "external_urls")
    val externalUrls: ExternalUrlsAlbums? = null,

    val href: String? = null,
    val id: String? = null,
    val images: List<ImageAlbum>? = null,
    val name: String? = null,

    @SerializedName( "release_date")
    val releaseDate: String? = null,

    @SerializedName( "release_date_precision")
    val releaseDatePrecision: ReleaseDatePrecision? = null,

    val type: AlbumGroup? = null,
    val uri: String? = null,
    val artists: List<ArtistAlbum>? = null,

    @SerializedName( "album_group")
    val albumGroup: AlbumGroup? = null
) : Parcelable

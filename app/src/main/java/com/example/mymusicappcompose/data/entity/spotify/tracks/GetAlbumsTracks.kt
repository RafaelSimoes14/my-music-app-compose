package com.example.mymusicappcompose.data.entity.spotify.tracks

data class GetAlbumsTracks(
    val href: String? = null,
    val items: List<Track>,
    val limit: Long? = null,
    val next: String? = null,
    val offset: Long? = null,
    val previous: Any? = null,
    val total: Long? = null
)
